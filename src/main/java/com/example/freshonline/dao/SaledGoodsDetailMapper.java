package com.example.freshonline.dao;

import com.example.freshonline.dto.SaledGoodsDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaledGoodsDetailMapper {
    public List<SaledGoodsDetail> selectByOrderId(@Param("order_id") Integer orderId);
}
