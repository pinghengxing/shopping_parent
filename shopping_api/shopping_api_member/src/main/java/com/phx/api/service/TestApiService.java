package com.phx.api.service;

import com.phx.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
public interface TestApiService {
    @RequestMapping("/test")
    public ResponseBase test(Integer id , String name);

    @RequestMapping("/setRedisTest")
    public ResponseBase setRedisTest(String key, String value);

    @RequestMapping("/getRedis")
    public ResponseBase getRedis(String key);
}
