package com.ch.money.service;

import com.ch.money.model.FinanceAccount;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 账户业务接口
 */
public interface FinanceService {

    /**
     * 登录后：根据用户编号查询用余额
     * @param userId
     * @return
     */
    FinanceAccount queryFinanceByUserId(Integer userId);
}
