package com.example.freshonline.service;


import com.example.freshonline.dao.CartMapper;
import com.example.freshonline.model.Cart;

import com.example.freshonline.model.CartExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    public Cart getCartEntry(Integer userId, Integer goodsId) {
        return cartMapper.selectByPrimaryKey(userId, goodsId);
    }

    public void batchDeleteFromCart(Integer userId, List<Integer> goodsIdList){
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdIn(goodsIdList).andUserIdEqualTo(userId);
        cartMapper.deleteByExample(example);
    }




}
