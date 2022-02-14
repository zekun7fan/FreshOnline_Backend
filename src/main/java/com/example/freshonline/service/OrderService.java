package com.example.freshonline.service;

import com.example.freshonline.dao.OrderDetailMapper;
import com.example.freshonline.dao.OrderMapper;
import com.example.freshonline.dto.OrderDetail;
import com.example.freshonline.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public List<OrderDetail> getOrderByUserId(Integer userId, Integer position){
        return orderDetailMapper.selectByUserId(userId,position);
    }
}
