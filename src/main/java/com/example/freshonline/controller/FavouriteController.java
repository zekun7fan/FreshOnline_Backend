package com.example.freshonline.controller;

import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.service.FavouriteService;
import com.example.freshonline.model.Favorite;

import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;

@RestController
public class FavouriteController {
    
    @Autowired
    private FavouriteService favouriteService;

    @GetMapping("/favourite")
    @PreAuthorize("checkUserId(#userId, #session)")
    public JSONObject checkFavourite(@RequestParam("user_id") Integer userId, @RequestParam("goods_id") Integer goodsId, HttpSession session) {
        boolean res = favouriteService.isFavourite(userId, goodsId);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }


    @PutMapping("/favourite")
    @PreAuthorize("checkUserId(#favorite.userId, #session)")
    public JSONObject addFavEntry(@RequestBody Favorite favorite, HttpSession session) {
        boolean res = favouriteService.addFavourite(favorite);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }


    @DeleteMapping("/favourite")
    @PreAuthorize("checkUserId(#favorite.userId, #session)")
    public JSONObject deleteFavEntry(@RequestBody Favorite favorite, HttpSession session) {
        boolean res = favouriteService.deleteFavourite(favorite);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }
    
}
