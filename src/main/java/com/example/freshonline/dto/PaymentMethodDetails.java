package com.example.freshonline.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDetails {

    private Integer cardNumber;

    private Integer cvvCode;
}
