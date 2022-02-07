package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.service.CartService;
import com.example.freshonline.service.StockedGoodsService;
import com.example.freshonline.utils.PicUtils;
import com.example.freshonline.utils.RespBuilder;
import com.example.freshonline.utils.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.freshonline.model.Cart;
import com.example.freshonline.model.joined_tables.GoodsCategory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Josh Sun
 */
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    static public class ID {
        private int userId;
        private int goodsId;

        public ID() {
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }
    }

    @GetMapping("/cart")
    public JSONObject getGoodsDetails(@RequestBody JSONObject req) {
        JSONObject res = new JSONObject();

        try {
            ID id = req.toJavaObject(ID.class);
            List<Cart> cart_goods = cartService.getCart(id.getUserId());
            JSONArray data = (JSONArray) JSONArray.toJSON(cart_goods);
            res.put("code", 0);
            res.put("data", data);
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

    @PutMapping("/cart")
    public JSONObject addCartEntry(@RequestBody JSONObject req) {
        JSONObject res = new JSONObject();

        try {
            Cart cart = req.toJavaObject(Cart.class);
            cartService.addToCart(cart);
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

    @PostMapping("/cart")
    public JSONObject updateCartEntry(@RequestBody JSONObject req) {
        JSONObject res = new JSONObject();

        try {
            Cart cart = req.toJavaObject(Cart.class);
            cartService.updateToCart(cart);
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

    @DeleteMapping("/cart")
    public JSONObject deleteCartEntry(@RequestBody JSONObject req) {
        JSONObject res = new JSONObject();

        try {
            ID id = req.toJavaObject(ID.class);
            cartService.deleteFromCart(id.getUserId(),id.getGoodsId());
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
