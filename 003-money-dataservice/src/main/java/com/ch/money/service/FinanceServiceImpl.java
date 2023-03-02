package com.ch.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ch.money.mapper.FinanceAccountMapper;
import com.ch.money.model.FinanceAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 账户业务实现类
 */
@Service(interfaceClass = FinanceService.class , version = "1.0.0" ,timeout = 20000)
@Component
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    FinanceAccountMapper financeAccountMapper;

    /**
     * 登录后：根据用户编号查询用余额
     * @param userId
     * @return
     */
    @Override
    public FinanceAccount queryFinanceByUserId(Integer userId) {
        FinanceAccount financeAccount = financeAccountMapper.selectFinanceByUserId(userId);
        return financeAccount;
    }
}
