package com.example.freshonline.dto;

import java.math.BigDecimal;

public class SearchResultInfo {
    private BigDecimal min_price;
    private BigDecimal max_price;
    private Integer goods_total;
    private String brand_list;

    public BigDecimal getMin_price() {
        return min_price;
    }

    public void setMin_price(BigDecimal min_price) {
        this.min_price = min_price;
    }

    public BigDecimal getMax_price() {
        return max_price;
    }

    public void setMax_price(BigDecimal max_price) {
        this.max_price = max_price;
    }

    public Integer getGoods_total() {
        return goods_total;
    }

    public void setGoods_total(Integer goods_total) {
        this.goods_total = goods_total;
    }

    public String getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(String brand_list) {
        this.brand_list = brand_list;
    }

    @Override
    public String toString(){
        return "min_price = " + min_price + " max_price = " + max_price + " goods_total = "
                + goods_total + " brand_list = " + brand_list;
    }
}
