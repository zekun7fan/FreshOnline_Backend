package com.example.freshonline.dao;

import com.example.freshonline.model.Cart;
import com.example.freshonline.model.CartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    long countByExample(CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int deleteByExample(CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int insertSelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    List<Cart> selectByExample(CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    Cart selectByPrimaryKey(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByPrimaryKeySelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByPrimaryKey(Cart record);
}