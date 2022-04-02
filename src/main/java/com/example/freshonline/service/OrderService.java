package com.example.freshonline.service;

import com.example.freshonline.dao.OrderDetailMapper;
import com.example.freshonline.dao.OrderMapper;
import com.example.freshonline.dto.OrderDetail;
import com.example.freshonline.enums.OrderStatus;
import com.example.freshonline.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMapper orderMapper;

    public List<OrderDetail> getOrderByUserId(Integer userId, Integer position){
        return orderDetailMapper.selectByUserId(userId,position);
    }


    public Integer createOrder(Integer userId, String location){
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus((byte) OrderStatus.CREATED.getStatus());
        order.setLocation(location);
        orderMapper.insertSelective(order);
        return order.getOrderId();
    }
}
