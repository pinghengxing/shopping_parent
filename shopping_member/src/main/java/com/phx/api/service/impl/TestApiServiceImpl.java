package com.phx.api.service.impl;

import com.phx.api.service.TestApiService;
import com.phx.base.BaseController;
import com.phx.base.ResponseBase;
import com.phx.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApiServiceImpl extends BaseController implements TestApiService {
    @Autowired
    private RedisService redisService;
    @Override
    public ResponseBase test(Integer id, String name) {
       return setResultSuccess();
    }

    @Override
    public ResponseBase setRedisTest(String key, String value) {
        redisService.setString(key, value);
        return setResultSuccess();
    }

    @Override
    public ResponseBase getRedis(String key) {
        String value = redisService.getString(key);
        return setResultSuccess(value);
    }
}
