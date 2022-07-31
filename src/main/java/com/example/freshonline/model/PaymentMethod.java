package com.example.freshonline.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_method.id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_method.user_id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_method.payment_type
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    private Byte paymentType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_method.details
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    private String details;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_method.id
     *
     * @return the value of payment_method.id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_method.id
     *
     * @param id the value for payment_method.id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_method.user_id
     *
     * @return the value of payment_method.user_id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_method.user_id
     *
     * @param userId the value for payment_method.user_id
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_method.payment_type
     *
     * @return the value of payment_method.payment_type
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public Byte getPaymentType() {
        return paymentType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_method.payment_type
     *
     * @param paymentType the value for payment_method.payment_type
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public void setPaymentType(Byte paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_method.details
     *
     * @return the value of payment_method.details
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public String getDetails() {
        return details;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_method.details
     *
     * @param details the value for payment_method.details
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    public void setDetails(String details) {
        this.details = details;
    }
}