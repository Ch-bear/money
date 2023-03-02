package com.ch.money.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ch.money.model.BidInfo;
import com.ch.money.model.LoanInfo;
import com.ch.money.model.PageModel;
import com.ch.money.service.BidInfoService;
import com.ch.money.service.LoanInfoService;
import com.ch.money.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoanController {
    @Reference(interfaceClass = LoanInfoService.class ,version = "1.0.0" , timeout = 2000)
    LoanInfoService loanInfoService;
    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0" , timeout = 2000)
    BidInfoService bidInfoService;
    @Reference(interfaceClass = UserService.class,version = "1.0.0" , timeout = 2000)
    UserService userService;

    @GetMapping("/loan/loan")
    public String loan(@RequestParam(name = "ptype",required = false)Integer ptype , Long cunPage, Model model , HttpServletRequest request){
        //1、todo:展现产品
//        Map<String,Object> parasMap = new HashMap<>();
//        parasMap.put("ptype", ptype);
//        parasMap.put("start", 0);
//        parasMap.put("content",9);

        //获取页面模型
        PageModel pageModel  = (PageModel)request.getSession().getAttribute("pageModel");
        //如果模型为空则证明是初次访问，创建模型对象并存入session
        if (null  == pageModel){
            pageModel = new PageModel();
            request.getSession().setAttribute("pageModel", pageModel);
        }
        //如果没传入页码或传入页码小于1，默认cunPage指向page第一页
        if(cunPage == null || cunPage <1){
            cunPage =pageModel.getFirstPage().longValue();
        }


        //如果页面模型的总页数为空或小于0，查出真实页面上限
        if (  null == pageModel.getTotalPage() || pageModel.getTotalPage()<=0){
            Long totalCount = loanInfoService.queryLoanInfoCountByType(ptype);
            //设置总记录数
            pageModel.setTotalCount(totalCount);
        }

        //访问页cunPage超页面模型的最后页，将cunPage指向最后页
        if(cunPage>pageModel.getTotalPage()){
            cunPage = pageModel.getTotalPage();
        }
        //将cunPage传至页面模型的当前页
        pageModel.setCunPage(cunPage);

//        List<LoanInfo> loanInfoList = loanInfoService.queryLoanInfosByTypeAndNum(parasMap);
        List<LoanInfo> loanInfoList = loanInfoService.queryLoanInfosByTypeAndPageModel(ptype,pageModel);
        model.addAttribute("loanInfoList", loanInfoList);
        model.addAttribute("ptype", ptype);
        model.addAttribute("pageModel", pageModel);
        //2、todo:投资排行榜
        return "loan";
    }

    @GetMapping("/loan/loanInfo")
    public String loanInfo(@RequestParam(name = "loanId" , required = true)Integer loanId,Model model){
        //查询商品信息
        LoanInfo loanInfo = loanInfoService.queryLoanInfoById(loanId);
        model.addAttribute("loanInfo",loanInfo);

        //根据产品编号查询产品的投资记录
        List<BidInfo> bidInfoList = bidInfoService.queryBidInfosByLoanId(loanId);
        model.addAttribute("bidInfoList", bidInfoList);

        return "loanInfo";
    }


}