package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.CartGoods;
import com.example.freshonline.service.CartService;

import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.freshonline.model.Cart;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;



    @GetMapping("/cart_element")
    @PreAuthorize("checkUserId(#userId, #session)")
    public JSONObject getCartGood(@RequestParam("user_id") Integer userId, @RequestParam("goods_id") Integer goodsId, HttpSession session) {
        Cart cartGoods = cartService.getCartEntry(userId, goodsId);
        return RespBuilder.create(cartGoods, VerifyRule.NOT_NULL);
    }

    @GetMapping("/cart")
    @PreAuthorize("checkUserId(#userId, #session)")
    public JSONObject getCartGoods(@RequestParam("user_id") Integer userId, HttpSession session) {
        List<Cart> list = cartService.getCart(userId);
        return RespBuilder.create(list, VerifyRule.NOT_NULL);
    }

    @PutMapping("/cart")
    @PreAuthorize("checkUserId(#cart.userId, #session)")
    public JSONObject addCartEntry(@RequestBody Cart cart, HttpSession session) {
        boolean res = cartService.addToCart(cart);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @PostMapping("/cart")
    @PreAuthorize("checkUserId(#cart.userId, #session)")
    public JSONObject updateCartEntry(@RequestBody Cart cart, HttpSession session) {
        boolean res = cartService.updateToCart(cart);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @DeleteMapping("/cart")
    @PreAuthorize("checkUserId(#cartGoods.userId, #session)")
    public JSONObject deleteCartEntry(@RequestBody CartGoods cartGoods, HttpSession session) {
        boolean res = cartService.deleteFromCart(cartGoods.getUserId(),cartGoods.getGoodsId());
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

}
