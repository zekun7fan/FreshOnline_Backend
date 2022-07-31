package com.example.freshonline.dto;
import com.example.freshonline.model.SaledGoods;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaledGoodsDetail extends SaledGoods {

    public String name;
    public String pic;
//    public BigDecimal storage;
}
