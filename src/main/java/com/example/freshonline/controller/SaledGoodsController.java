package com.example.freshonline.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.SaledGoods;
import com.example.freshonline.service.SaledGoodsService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SaledGoodsController {

    @Autowired
    private SaledGoodsService saledGoodsService;

    @PutMapping("/saledGoods")
    public JSONObject updateSaledGoods(@RequestBody SaledGoods goods) {
        goods.setReviewtime(LocalDateTime.now());
        goods.setReviewed(true);
        boolean res = saledGoodsService.updateSaledGoods(goods);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }


    @GetMapping("/saledGoods")
    public JSONObject getFeedBacks(@RequestParam("goodsId") Integer goodsId) {
        List<SaledGoods> list = saledGoodsService.getSaledGoods(goodsId);
        return RespBuilder.create(list, VerifyRule.NOT_NULL);
    }

}
