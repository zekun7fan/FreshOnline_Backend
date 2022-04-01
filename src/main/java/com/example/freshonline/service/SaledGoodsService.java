package com.example.freshonline.service;


import com.example.freshonline.dao.SaledGoodsMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.model.SaledGoods;
import com.example.freshonline.model.StockedGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Service
public class SaledGoodsService {

    @Autowired
    private SaledGoodsMapper saledGoodsMapper;

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    public void addSaledGoods(Integer orderId, Map<Integer, BigDecimal> map){
        if (map == null){
            return;
        }
        Set<Map.Entry<Integer, BigDecimal>> entries = map.entrySet();
        for (Map.Entry<Integer, BigDecimal> entry : entries){
            Integer goodsId = entry.getKey();
            BigDecimal count = entry.getValue();
            StockedGoods stockedGoods = stockedGoodsMapper.selectByPrimaryKey(goodsId);
            BigDecimal price = stockedGoods.getPrice();
            BigDecimal salePrice = stockedGoods.getSalePrice();
            BigDecimal priceOnPurchase = salePrice != null ? salePrice : price;
            SaledGoods saledGoods = new SaledGoods();
            saledGoods.setOrderId(orderId);
            saledGoods.setGoodsId(goodsId);
            saledGoods.setPrice(priceOnPurchase);
            saledGoods.setCount(count);
            saledGoodsMapper.insertSelective(saledGoods);
        }

    }

}
