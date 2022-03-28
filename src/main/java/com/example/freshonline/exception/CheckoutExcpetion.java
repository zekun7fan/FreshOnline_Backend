package com.example.freshonline.exception;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CheckoutExcpetion extends RuntimeException{
    private String message = "error during checkout";
    private List<Integer> errorGoodsList;
    public CheckoutExcpetion(String message, List<Integer> errorGoodsList) {
        this.message = message;
        this.errorGoodsList = errorGoodsList;
    }
    public CheckoutExcpetion(List<Integer> goodslist){
        this.errorGoodsList = goodslist;
    }
}
