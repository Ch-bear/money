package com.ch.money.mapper;

import com.ch.money.model.RechargeRecord;

public interface RechargeRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_recharge_record
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_recharge_record
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int insert(RechargeRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_recharge_record
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int insertSelective(RechargeRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_recharge_record
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    RechargeRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_recharge_record
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int updateByPrimaryKeySelective(RechargeRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_recharge_record
     *
     * @mbg.generated Thu Jul 07 19:42:13 CST 2022
     */
    int updateByPrimaryKey(RechargeRecord record);
}