package com.example.freshonline.service;

import com.example.freshonline.dao.OrderMapper;
import com.example.freshonline.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public List<Order> getOrderByUserId(Integer userId){
        return orderMapper.selectByUserId(userId);
    }
}
