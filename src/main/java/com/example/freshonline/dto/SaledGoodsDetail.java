package com.example.freshonline.dto;
import com.example.freshonline.model.SaledGoods;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaledGoodsDetail extends SaledGoods {
    @Getter
    @Setter
    public String name;
}
