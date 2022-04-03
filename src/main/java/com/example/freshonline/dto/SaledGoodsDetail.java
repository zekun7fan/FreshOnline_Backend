package com.example.freshonline.dto;
import com.example.freshonline.model.SaledGoods;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaledGoodsDetail extends SaledGoods {
    @Getter
    @Setter
    public String name;
    @Getter
    @Setter
    public String pic;
    @Getter
    @Setter
    public BigDecimal storage;
}
