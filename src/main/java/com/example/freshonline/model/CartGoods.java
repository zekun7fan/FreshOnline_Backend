package com.example.freshonline.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartGoods {
    private int userId;
    private int goodsId;

}
