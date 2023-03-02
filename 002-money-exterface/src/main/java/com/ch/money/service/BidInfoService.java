package com.ch.money.service;

import com.ch.money.model.BidInfo;

import java.util.List;
import java.util.Map;


public interface BidInfoService {
    /**
     * 投资累计总金额
     * @return
     */
    Double queryBidMoneySum();

    /**
     * 根据产品编号查询产品的投资记录
     * @param loanId 产品编号
     * @return 投资列表
     */
    List<BidInfo> queryBidInfosByLoanId(Integer loanId);

    /**
     * 投资：投资
     * @param parasMap
     * @return
     */
    String invest(Map<String, Object> parasMap);
}
