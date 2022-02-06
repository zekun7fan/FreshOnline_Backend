package com.example.freshonline.model;

import java.time.LocalDateTime;

public class Order {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.order_id
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.user_id
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.payment_method_id
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    private Integer paymentMethodId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.order_time
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    private LocalDateTime orderTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.pay_time
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    private LocalDateTime payTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.finish_time
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    private LocalDateTime finishTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.status
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    private Byte status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.location
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    private String location;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.order_id
     *
     * @return the value of order.order_id
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.order_id
     *
     * @param orderId the value for order.order_id
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.user_id
     *
     * @return the value of order.user_id
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.user_id
     *
     * @param userId the value for order.user_id
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.payment_method_id
     *
     * @return the value of order.payment_method_id
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.payment_method_id
     *
     * @param paymentMethodId the value for order.payment_method_id
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.order_time
     *
     * @return the value of order.order_time
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.order_time
     *
     * @param orderTime the value for order.order_time
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.pay_time
     *
     * @return the value of order.pay_time
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public LocalDateTime getPayTime() {
        return payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.pay_time
     *
     * @param payTime the value for order.pay_time
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.finish_time
     *
     * @return the value of order.finish_time
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.finish_time
     *
     * @param finishTime the value for order.finish_time
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.status
     *
     * @return the value of order.status
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.status
     *
     * @param status the value for order.status
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.location
     *
     * @return the value of order.location
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.location
     *
     * @param location the value for order.location
     *
     * @mbg.generated Sun Feb 06 16:46:40 EST 2022
     */
    public void setLocation(String location) {
        this.location = location;
    }
}