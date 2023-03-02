package com.ch.money.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ch.money.model.FinanceAccount;
import com.ch.money.model.User;
import com.ch.money.service.FinanceService;
import com.ch.money.utils.Constants;
import com.ch.money.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FinanceController {
    @Reference(interfaceClass = FinanceService.class , version = "1.0.0" ,timeout = 20000)
    FinanceService financeService;

    @GetMapping("/loan/page/queryFinance")
    @ResponseBody
    public Object queryFinance(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute(Constants.LOGIN_USER);
        if (user == null){
            return Result.error("请先登录后再查询");
        }
        FinanceAccount financeAccount = financeService.queryFinanceByUserId(user.getId());
//        FinanceAccount financeAccount = financeService.queryFinanceByUserId(user.getId());
        if (financeAccount == null){
            return Result.error("系统繁忙，请稍后重试或联系客服...");
        }
        return Result.success(financeAccount.getAvailableMoney()+"");
    }
}
