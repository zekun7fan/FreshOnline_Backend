package com.example.freshonline.dao;

import com.example.freshonline.model.PaymentMethod;
import com.example.freshonline.model.PaymentMethodExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaymentMethodMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    long countByExample(PaymentMethodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int deleteByExample(PaymentMethodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int insert(PaymentMethod record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int insertSelective(PaymentMethod record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    List<PaymentMethod> selectByExampleWithBLOBs(PaymentMethodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    List<PaymentMethod> selectByExample(PaymentMethodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    PaymentMethod selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByExampleSelective(@Param("record") PaymentMethod record, @Param("example") PaymentMethodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByExampleWithBLOBs(@Param("record") PaymentMethod record, @Param("example") PaymentMethodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByExample(@Param("record") PaymentMethod record, @Param("example") PaymentMethodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByPrimaryKeySelective(PaymentMethod record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByPrimaryKeyWithBLOBs(PaymentMethod record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_method
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByPrimaryKey(PaymentMethod record);
}