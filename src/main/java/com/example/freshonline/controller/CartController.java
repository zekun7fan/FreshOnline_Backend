package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.freshonline.model.Cart;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    static public class Id {
        private int userId;
        private int goodsId;

        public Id() {
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

     
    @GetMapping("/cart_element")
    public JSONObject getCartGood(@RequestParam("user_id") String user_id, @RequestParam("goods_id") String goods_id) {
        JSONObject res = new JSONObject();

        try {
            Integer userId = Integer.parseInt(user_id);
            Integer goodsId = Integer.parseInt(goods_id);
            Cart cart_goods = cartService.getCartEntry(userId,goodsId);
        
            JSONObject data = (JSONObject) JSONObject.toJSON(cart_goods);
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

    @GetMapping("/cart")
    public JSONObject getCartGoods(@RequestParam("user_id") String user_id) {
        JSONObject res = new JSONObject();

        try {
            Integer id = Integer.parseInt(user_id);
            List<Cart> cart_goods = cartService.getCart(id);
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
            Id id = req.toJavaObject(Id.class);
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
