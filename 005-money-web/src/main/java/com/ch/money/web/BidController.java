package com.ch.money.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ch.money.model.LoanInfo;
import com.ch.money.model.User;
import com.ch.money.service.BidInfoService;
import com.ch.money.service.LoanInfoService;
import com.ch.money.utils.Constants;
import com.ch.money.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BidController {
    @Reference(interfaceClass = LoanInfoService.class ,version = "1.0.0" , timeout = 2000)
    LoanInfoService loanInfoService;
    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0" , timeout = 2000)
    BidInfoService bidInfoService;
    @PostMapping("/loan/page/invest")
    @ResponseBody
    public Object invest(@RequestParam(name = "bidMoney") Double bidMoney, @RequestParam(name = "loanId")Integer loanId, HttpServletRequest request){
        //是否登录
        User user = (User)request.getSession().getAttribute(Constants.LOGIN_USER);
        if (user == null){
            System.out.println("Controller====未登录");
            return Result.error("请先登录后再进行投资");
        }
        //是否实名认证
        //常规验证
        LoanInfo loanInfo = loanInfoService.queryLoanInfoById(loanId);
        if (bidMoney > loanInfo.getLeftProductMoney()){
            System.out.println("Controller====超出限制");
            return Result.error("输入的金额应在"+loanInfo.getBidMinLimit()+"——"+loanInfo.getBidMaxLimit()+"之间");
//            return Result.error("输入的金额应在"+loanInfo.getBidMinLimit()+"——"+loanInfo.getBidMaxLimit()+"之间");
        }
        Map<String,Object> parasMap = new HashMap<>();
        parasMap.put("key", "value");
        parasMap.put("bidMoney", bidMoney);
        parasMap.put("loanId", loanId);
        parasMap.put("userId",user.getId());
        //调用service
        System.out.println("Controller====调用service");
        String result = bidInfoService.invest(parasMap);
        if (!"ok".equals(result)){
            System.out.println("Controller====宣告失败");
            return Result.error(result);
        }
        //
        System.out.println("Controller====宣告成功");
        return Result.success("成功了");
    }

}
