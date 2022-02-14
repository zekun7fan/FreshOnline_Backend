package com.example.freshonline.dto;

import com.example.freshonline.model.Order;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail extends Order {
    @Getter
    @Setter
    public List<SaledGoodsDetail> goodsList;
}
