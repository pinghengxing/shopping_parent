package com.phx.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

//@Component
public class BaseRedisService {
//	@Autowired
    //使用方自己进行注入
	public StringRedisTemplate stringRedisTemplate;

	public void setString(String key, Object data) {
		setString(key, data, null);
	}

	public void setString(String key, Object data, Long timeout) {
		if (data instanceof String) {
			String value = (String) data;
			stringRedisTemplate.opsForValue().set(key, value);
		}
		if (timeout != null) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	public String getString(String key) {
		return (String) stringRedisTemplate.opsForValue().get(key);
	}

	public void delKey(String key) {
		stringRedisTemplate.delete(key);
	}

}
