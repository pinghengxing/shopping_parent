package com.phx.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.phx.api.service.MemberService;
import com.phx.base.BaseController;
import com.phx.base.ResponseBase;
import com.phx.constants.Constants;
import com.phx.dao.MemberDao;
import com.phx.entity.UserEntity;
import com.phx.mq.RegisterMailboxProducer;
import com.phx.redis.RedisService;
import com.phx.utils.MD5Util;
import com.phx.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class MemberServiceImpl extends BaseController implements MemberService {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private RegisterMailboxProducer registerMailboxProducer;
	@Value("${messages.queue}")
	private String messages_queue;

    @Autowired
    private RedisService redisService;


	@Override
	public ResponseBase findUserById(Long userId) {
		UserEntity user = memberDao.findByID(userId);
		if (user == null) {
			return setResultError("未查找到用户信息.");
		}
		return setResultSuccess(user);
	}

    @Override
    public ResponseBase regUser(UserEntity user) {
        // 参数验证
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空.");
        }
        String newPassword = MD5Util.MD5(password);
        user.setPassword(newPassword);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        Integer result = memberDao.insertUser(user);
        if (result <= 0) {
            return setResultError("注册用户信息失败.");
        }
        // 采用异步方式发送消息
        String email = user.getEmail();
        String json = emailJson(email);
        log.info("####会员服务推送消息到消息服务平台####json:{}", json);
        sendMsg(json);
        return setResultSuccess("用户注册成功.");
    }

    private String emailJson(String email) {
        JSONObject rootJson = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", Constants.MSG_EMAIL);
        JSONObject content = new JSONObject();
        content.put("email", email);
        rootJson.put("header", header);
        rootJson.put("content", content);
        return rootJson.toJSONString();
    }

    private void sendMsg(String json) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(messages_queue);
        registerMailboxProducer.sendMsg(activeMQQueue, json);

    }
    @Override
    public ResponseBase login(UserEntity user) {
        // 1.验证参数
        String username = user.getUsername();
        if (StringUtils.isEmpty(username)) {
            return setResultError("用戶名称不能为空!");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        // 2.数据库查找账号密码是否正确
        String newPassWrod = MD5Util.MD5(password);
        UserEntity userEntity = memberDao.login(username, newPassWrod);
        if (userEntity == null) {
            return setResultError("账号或者密码不正确");
        }
        return setLogin(userEntity);
    }

    @Override
    public ResponseBase findByTokenUser(String token) {
        // 1.验证参数
        if (StringUtils.isEmpty(token)) {
            return setResultError("token不能为空!");
        }
        // 2.从redis中 使用token 查找对应 userid
        String strUserId = (String) redisService.getString(token);
        if (StringUtils.isEmpty(strUserId)) {
            return setResultError("token无效或者已经过期!");
        }
        // 3.使用userid数据库查询用户信息返回给客户端
        Long userId = Long.parseLong(strUserId);
        UserEntity userEntity = memberDao.findByID(userId);
        if (userEntity == null) {
            return setResultError("未查找到该用户信息");
        }
        userEntity.setPassword(null);
        return setResultSuccess(userEntity);
    }
    @Override
    public ResponseBase findByOpenIdUser(String openid) {
        // 1.验证参数
        if (StringUtils.isEmpty(openid)) {
            return setResultError("openid不能为空!");
        }
        // 2.使用openid 查询数据库 user表对应数据信息
        UserEntity userEntity = memberDao.findByOpenIdUser(openid);
        if (userEntity == null) {
            return setResultError(Constants.HTTP_RES_CODE_201, "该openid没有关联");
        }
        // 3.自动登录
        return setLogin(userEntity);
    }

    @Override
    public ResponseBase qqLogin( UserEntity user) {
        // 1.验证参数
        String openid = user.getOpenid();
        if (StringUtils.isEmpty(openid)) {
            return setResultError("openid不能为空!");
        }
        //  2.先进行账号登陆
        ResponseBase setLogin = login(user);
        if (!setLogin.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            return setLogin;
        }
        JSONObject jsonObjcet = (JSONObject) setLogin.getData();
        // 3. 获取token信息
        String memberToken = jsonObjcet.getString("memberToken");
        ResponseBase userToken = findByTokenUser(memberToken);
        if (!userToken.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            return userToken;
        }
        UserEntity userEntity = (UserEntity) userToken.getData();
        // 5.修改用户openid
        Integer userId = userEntity.getId();
        Integer updateByOpenIdUser = memberDao.updateByOpenIdUser(openid, userId);
        if (updateByOpenIdUser <= 0) {
            return setResultError("QQ账号关联失败!");
        }
        return setLogin;
    }

    private ResponseBase setLogin(UserEntity userEntity) {
        if (userEntity == null) {
            return setResultError("未查找到此用户相关信息！");
        }
        // 3.如果账号密码正确或者openId存在，对应生成token
        String memberToken = TokenUtils.getMemberToken();
        // 4.存放在redis中，key为token value 为 userid
        Integer userId = userEntity.getId();
        log.info("####用户信息token存放在redis中... key为:{},value", memberToken, userId);
        redisService.setString(memberToken, userId + "", Constants.TOKEN_MEMBER_TIME);
        // 5.直接返回token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberToken", memberToken);
        return setResultSuccess(jsonObject);
    }
}
