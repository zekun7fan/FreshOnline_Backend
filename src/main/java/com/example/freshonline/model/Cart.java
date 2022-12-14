package com.example.freshonline.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private User user;

    private StockedGoods stockedGoods;


    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.user_id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.goods_id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    private Integer goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.count
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    private BigDecimal count;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.user_id
     *
     * @return the value of cart.user_id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.user_id
     *
     * @param userId the value for cart.user_id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.goods_id
     *
     * @return the value of cart.goods_id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.goods_id
     *
     * @param goodsId the value for cart.goods_id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.count
     *
     * @return the value of cart.count
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public BigDecimal getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.count
     *
     * @param count the value for cart.count
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public void setCount(BigDecimal count) {
        this.count = count;
    }
}