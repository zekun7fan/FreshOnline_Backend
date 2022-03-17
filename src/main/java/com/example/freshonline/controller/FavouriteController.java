package com.example.freshonline.controller;

import com.example.freshonline.service.FavouriteService;
import com.example.freshonline.model.Favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
public class FavouriteController {
    
    @Autowired
    private FavouriteService favouriteService;

    @GetMapping("/favourite")
    public JSONObject checkFavourite(@RequestParam("user_id") String user_id, @RequestParam("goods_id") String goods_id) {
        JSONObject res = new JSONObject();

        try {
            Integer userID = Integer.parseInt(user_id);
            Integer goodsID = Integer.parseInt(goods_id);
            
            res.put("code", 0);
            res.put("result", favouriteService.isFavourite(userID, goodsID));
            return res;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            res.put("code", 1);
            res.put("msg", sw.toString());
            return res;

        }
    }


    @PutMapping("/favourite")
    public JSONObject addFavEntry(@RequestBody JSONObject req) {
        JSONObject res = new JSONObject();

        try {
            Favorite favorite = req.toJavaObject(Favorite.class);
            favouriteService.addFavourite(favorite);
            res.put("code", 0);
            return res;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            res.put("code", 1);
            res.put("msg", sw.toString());
            return res;

        }
    }


    @DeleteMapping("/favourite")
    public JSONObject deleteFavEntry(@RequestBody JSONObject req) {
        JSONObject res = new JSONObject();

        try {
            Favorite favorite = req.toJavaObject(Favorite.class);
            favouriteService.deleteFavourite(favorite);
            res.put("code", 0);
            return res;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            res.put("code", 1);
            res.put("msg", sw.toString());
            return res;
        }
    }
    
}
