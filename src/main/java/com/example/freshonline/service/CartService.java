package com.example.freshonline.service;


import com.example.freshonline.dao.CartMapper;
import com.example.freshonline.model.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    public void addToCart(Cart record){
        cartMapper.insertSelective(record);
    }

    public void updateToCart(Cart record){
        
        cartMapper.updateByPrimaryKeySelective(record);
    }


    public void deleteFromCart(int userId, int goodsId){
        cartMapper.deleteByPrimaryKey(userId, goodsId);
    }


    public List<Cart> getCart(int userId){
        return cartMapper.selectByUserID(userId);
    }




}
