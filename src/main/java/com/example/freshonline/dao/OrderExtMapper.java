package com.example.freshonline.dao;


import com.example.freshonline.model.Order;

import java.util.List;

public interface OrderExtMapper {

    List<Order> selectByUserId(Integer userId);
}
