package com.ch.money.service;

import com.ch.money.model.User;

/**
 * 用户业务结构
 */
public interface UserService {
    /**
     * 查询平台用户数
     * @return
     */
    Long queryUserCount();

    /**
     * 查手机号是否被注册
     * @param phone
     * @return
     */
    int checkPhone(String phone);

    /**
     * //进行手机号与密码注册
     * @param phone
     * @param loginPassword
     * @return
     */
    User register(String phone, String loginPassword);

    /**
     * 登录：根据手机号和密码登录
     * @param phone
     * @param loginPassword
     * @return
     */
    User login(String phone, String loginPassword);
}
