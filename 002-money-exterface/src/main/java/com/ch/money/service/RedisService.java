package com.ch.money.service;

/**
 * redis业务接口
 */
public interface RedisService {
    /**
     * 注册：存放短信验证码
     * @param phone
     * @param code
     */
    void push(String phone , String code );


    /**
     * 注册：获取redis中存储的短信验证码
     * @param phone
     * @return
     */
    String pop(String phone);
}
