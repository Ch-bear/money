package com.ch.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis业务实现类
 */
@Service(interfaceClass = RedisService.class,version = "1.0.0" , timeout = 20000)
@Component
public class RedisServiceImpl implements RedisService {

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    //注册：存放短信验证码
    @Override
    public void push(String phone, String code) {
        redisTemplate.opsForValue().set(phone, code, 60*5, TimeUnit.SECONDS);
    }

    //注册：取出短信验证码
    @Override
    public String pop(String phone) {
        return (String)redisTemplate.opsForValue().get(phone);
    }
}
