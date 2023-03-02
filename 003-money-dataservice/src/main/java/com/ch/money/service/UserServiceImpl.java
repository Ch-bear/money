package com.ch.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ch.money.mapper.FinanceAccountMapper;
import com.ch.money.mapper.UserMapper;
import com.ch.money.model.FinanceAccount;
import com.ch.money.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 用户业务实现类
 */
@Service(interfaceClass = UserService.class , version = "1.0.0" , timeout = 20000)
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    FinanceAccountMapper financeAccountMapper;
    //查询平台用户数
    @Override
    public Long queryUserCount() {
        return userMapper.selectUserCount();
    }

    /**
     * 查手机号是否被注册
     * @param phone
     * @return
     */
    @Override
    public int checkPhone(String phone) {
        return userMapper.selectUserCountByPhone(phone);
    }

    /**
     * 注册：进行手机号与密码注册
     * @param phone
     * @param loginPassword
     * @return
     */
    @Override
    public User register(String phone, String loginPassword) {
        //注册
        User user = new User();
        user.setAddTime(new Date());
        user.setLoginPassword(loginPassword);
        user.setPhone(phone);
        int count = userMapper.insertSelective(user);

        //去送大礼
        if (count == 1){
            new Thread(new Runnable(){
                @Override
                public void run() {
                    //送大礼
                    FinanceAccount financeAccount = new FinanceAccount();
                    financeAccount.setAvailableMoney(888d);
                    financeAccount.setUid(user.getId());

                    financeAccountMapper.insertSelective(financeAccount);
                }
            }).start();
        }


        return user;
    }

    /**
     * 登录：根据手机号和密码登录
     * @param phone
     * @param loginPassword
     * @return
     */
    @Override
    public User login(String phone, String loginPassword) {
        User user = userMapper.selectUserByPhoneAndPasswd(phone,loginPassword);
        //更新登录上次时间

        if (user != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    user.setLastLoginTime(new Date());
                    userMapper.updateByPrimaryKeySelective(user);
                    userMapper.updateByPrimaryKeySelective(user);
                }
            }).start();
        }
        return user;
    }
}
