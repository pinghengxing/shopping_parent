package com.phx.controller;

import com.phx.base.ResponseBase;
import com.phx.constants.Constants;
import com.phx.fegin.PayCallBackFegin;
import com.phx.fegin.PayServiceFegin;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Controller
public class PayController {
    @Autowired
    private PayServiceFegin payServiceFegin;
    @Autowired
    private PayCallBackFegin payCallBackFegin;
    // 错误页面
    private static final String ERROR = "";
    private static final String PAY_SUCCESS = "pay_success";

    // 使用token 进行支付
    @RequestMapping("/aliPay")
    public void aliPay(String payToken, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        // 1.参数验证
        if (StringUtils.isEmpty(payToken)) {
            return;
        }
        // 2.调用支付服务接口 获取支付宝html元素
        ResponseBase payTokenResult = payServiceFegin.findPayToken(payToken);
        if (!payTokenResult.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            String msg = payTokenResult.getMsg();
            writer.println(msg);
            return;
        }
        // 3.返回可以执行的html元素给客户端
        LinkedHashMap data = (LinkedHashMap) payTokenResult.getData();
        String payHtml = (String) data.get("payHtml");
        log.info("####PayController###payHtml:{}", payHtml);
        // 4. 页面上进行渲染
        writer.println(payHtml);
        writer.close();
    }

    // 同步回调
    @RequestMapping("/callBack/synCallBack")
    public void synCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        PrintWriter writer = response.getWriter();
        ResponseBase synCallBack = payCallBackFegin.synCallBack(params);
        if (!synCallBack.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            return;
        }

        LinkedHashMap data = (LinkedHashMap) synCallBack.getData();
        String htmlFrom = "<form name='punchout_form'"
                + " method='post' action='http://127.0.0.1:81/callBack/synSuccessPage' >"
                + "<input type='hidden' name='outTradeNo' value='" + data.get("outTradeNo") + "'>"
                + "<input type='hidden' name='tradeNo' value='" + data.get("tradeNo") + "'>"
                + "<input type='hidden' name='totalAmount' value='" + data.get("totalAmount") + "'>"
                + "<input type='submit' value='立即支付' style='display:none'>"
                + "</form><script>document.forms[0].submit();" + "</script>";
        writer.println(htmlFrom);
        writer.close();
    }

    // 同步回调,解决隐藏参数
    @RequestMapping(value = "/callBack/synSuccessPage", method = RequestMethod.POST)
    public String synSuccessPage(HttpServletRequest request, String outTradeNo, String tradeNo, String totalAmount) {
        request.setAttribute("outTradeNo", outTradeNo);
        request.setAttribute("tradeNo", tradeNo);
        request.setAttribute("totalAmount", totalAmount);
        return PAY_SUCCESS;
    }

    // 异步回调
    @ResponseBody
    @RequestMapping("/callBack/asynCallBack")
    public String asynCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return payCallBackFegin.asynCallBack(params);
    }

}
