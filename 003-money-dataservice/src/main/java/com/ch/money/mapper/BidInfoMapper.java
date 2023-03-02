package com.ch.money.mapper;

import com.ch.money.model.BidInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resources;
import java.util.List;

@Repository
public interface BidInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int insert(BidInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int insertSelective(BidInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    BidInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int updateByPrimaryKeySelective(BidInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int updateByPrimaryKey(BidInfo record);

    //投资总金额
    Double selectBidMoneySum();

    /**
     * 根据产品编号查询产品的投资记录
     * @return
     */
    List<BidInfo> selectBidInfosByLoanId(Integer loanId);
}