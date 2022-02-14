package com.example.freshonline.dao;

import com.example.freshonline.dto.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailMapper {
    List<OrderDetail> selectByOrderId(@Param("order_id") Integer orderId);

    List<OrderDetail> selectByUserId(@Param("user_id") Integer userId, Integer position);
}
