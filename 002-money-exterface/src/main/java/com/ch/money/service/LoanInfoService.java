package com.ch.money.service;

import com.ch.money.model.LoanInfo;
import com.ch.money.model.PageModel;

import java.util.List;
import java.util.Map;

/**
 * 产品业务功能
 */
public interface LoanInfoService {

    /**
     * 查询历史年化利率
     * @return 历史年化利率
     */
    Double queryLoanInfoHistoryRateAvg();

    /**
     * 首页：根据产品类型和数量查询产品信息
     * @param parasMap sql查询语句参数
     * @return
     */
    List<LoanInfo> queryLoanInfosByTypeAndNum(Map<String, Object> parasMap);

    /**
     * 根据产品类型与分页模型查询产品信息
     * @param ptype
     * @param pageModel
     * @return
     */
    List<LoanInfo> queryLoanInfosByTypeAndPageModel(Integer ptype, PageModel pageModel);

    /**
     * 通过类型查出总记录条数
     * @param ptype
     * @return
     */
    Long queryLoanInfoCountByType(Integer ptype);

    /**
     * 根据id查询商品信息
     * @param loanId
     * @return
     */
    LoanInfo queryLoanInfoById(Integer loanId);
}
