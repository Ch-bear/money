package com.ch.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ch.money.mapper.BidInfoMapper;
import com.ch.money.mapper.FinanceAccountMapper;
import com.ch.money.mapper.LoanInfoMapper;
import com.ch.money.model.BidInfo;
import com.ch.money.model.LoanInfo;
import com.ch.money.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 投资业务实现类
 */
@Service(interfaceClass = BidInfoService.class,version = "1.0.0",timeout = 20000)
@Component
public class BidInfoServiceImpl implements BidInfoService {
    //
    @Autowired
    BidInfoMapper bidInfoMapper;
    @Autowired
    LoanInfoMapper loanInfoMapper;
    @Autowired
    FinanceAccountMapper financeAccountMapper;
    //投资累计总金额
    @Override
    public Double queryBidMoneySum() {
        return bidInfoMapper.selectBidMoneySum();
    }

    //根据产品编号查询产品的投资记录
    @Override
    public List<BidInfo> queryBidInfosByLoanId(Integer loanId) {

        return bidInfoMapper.selectBidInfosByLoanId(loanId);
    }

    //投资：投资
    @Override
    public String invest(Map<String, Object> parasMap) {

        //判断剩余可投金额
        LoanInfo loanInfo = loanInfoMapper.selectByPrimaryKey((Integer) parasMap.get("loanId"));
        if ((Double) parasMap.get("bidMoney") > loanInfo.getLeftProductMoney()){
            System.out.println("BidInfoService====可投金额不足");
//            return Result.error("输入的金额应在"+loanInfo.getBidMinLimit()+"——"+loanInfo.getBidMaxLimit()+"之间");
            return "输入的金额应在"+loanInfo.getBidMinLimit()+"--"+loanInfo.getBidMaxLimit()+"之间";
//            return "输入的金额应在"+loanInfo.getBidMinLimit()+"——"+loanInfo.getBidMaxLimit()+"之间";
        }

        //减少剩余金额
        int num1 = loanInfoMapper.updateLeftMoneyReduceForInvest(parasMap);
        System.out.println("bidInfoService====num1:"+num1);
        System.out.println("BidInfoService====num1:"+num1);
        //减少个人余额
        int num2 = financeAccountMapper.updateMoneyReduceForInvest(parasMap);
        System.out.println("BidInfoService====num2:"+num2);
        System.out.println("BidInfoService====num2:"+num2);
        //判断是否满标

        if (loanInfo.getLeftProductMoney() == 0d && loanInfo.getProductStatus() == 0){
//            return Result.error("输入的金额应在"+loanInfo.getBidMinLimit()+"——"+loanInfo.getBidMaxLimit()+"之间");
            loanInfo.setProductStatus(1);
            loanInfo.setProductFullTime(new Date());
            int num3 = loanInfoMapper.updateByPrimaryKeySelective(loanInfo);

            System.out.println("BidInfoService====num3:"+num3);

        }
        //添加个人投资记录
        BidInfo bidInfo = new BidInfo();
        bidInfo.setBidMoney((Double) parasMap.get("bidMoney"));
        bidInfo.setBidStatus(1);
        bidInfo.setBidTime(new Date());

        bidInfo.setLoanId((Integer) parasMap.get("loanId"));

        bidInfo.setLoanId((Integer) parasMap.get("loanId"));
        bidInfo.setUid((Integer) parasMap.get("userId"));

        int num4 = bidInfoMapper.insertSelective(bidInfo);

        System.out.println("BidInfoService====num4:"+num4);

        System.out.println("BidInfoService====num4:"+num4);

        return "ok";
    }
}
