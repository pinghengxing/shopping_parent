package com.phx.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisService extends BaseRedisService implements InitializingBean {
    @Autowired
    public StringRedisTemplate stringRedisTemplate;


    @Override
    public void afterPropertiesSet() throws Exception {
        super.stringRedisTemplate = this.stringRedisTemplate;
    }
}
