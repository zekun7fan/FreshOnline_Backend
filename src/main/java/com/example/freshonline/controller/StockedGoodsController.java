package com.example.freshonline.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.example.freshonline.constants.RespConstant;
import com.example.freshonline.dto.GoodsPicInfo;
import com.example.freshonline.dto.SearchParams;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.exception.CheckoutExcpetion;
import com.example.freshonline.model.Cart;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.service.CartService;
import com.example.freshonline.service.GoodsPictureService;
import com.example.freshonline.service.StockedGoodsService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.freshonline.dto.CheckOutInfo;
import com.example.freshonline.dto.CreateOrderDetail;
import com.example.freshonline.event.CustomEventPublisher;
import com.example.freshonline.model.joined_tables.GoodsCategory;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ServiceLoader;



@RestController
public class StockedGoodsController {

    @Autowired
    private StockedGoodsService stockedGoodsService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomEventPublisher customEventPublisher;

    @Autowired
    private GoodsPictureService pictureService;


    @PostMapping("/checkout")
    public JSONObject checkout(@RequestBody CheckOutInfo checkOutInfo) {
        List<Cart> cartlist = cartService.getCart(checkOutInfo.getId());
        List<String> failedNameList = stockedGoodsService.checkout(cartlist);
        if (!failedNameList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("the following goods are out of stock: ");
            for (String s : failedNameList) {
                sb.append(s);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return RespBuilder.createFailedRsp(sb.toString());
        }
        // send the event to event listener
        HashMap<Integer, BigDecimal> map = new HashMap<>();
        for (Cart c : cartlist) {
            map.put(c.getGoodsId(), c.getCount());
        }
        CreateOrderDetail detail = new CreateOrderDetail(checkOutInfo.getId(), checkOutInfo.getLocation(), map);
        customEventPublisher.publishCreateOrderEvent(detail);
        return RespBuilder.create(failedNameList, VerifyRule.COLLECTION_EMPTY);
    }


    @GetMapping("/goodsdetails/{goods_id}")
    public JSONObject getGoodsDetails(@PathVariable("goods_id") Integer goods_id) {
        JSONObject res = new JSONObject();
        GoodsCategory gc = stockedGoodsService.goodsDetails(goods_id);
        return RespBuilder.create(gc, VerifyRule.NOT_NULL);
    }


    @GetMapping("/goods")
    public JSONObject getSearch(@Validated SearchParams param) {
        param.computePage();
        JSONObject output = stockedGoodsService.getSearch(param);
        return RespBuilder.create(output, VerifyRule.NOT_NULL, RespConstant.OPERATE_SUCCESS, RespConstant.OPERATE_FAIL);
    }


    @GetMapping("/weekly_special")
    public JSONObject getWeeklySpecial() {
        return RespBuilder.create(stockedGoodsService.getWeeklySpecial(), VerifyRule.COLLECTION_NOT_EMPTY, "success", "fail");
    }


    @PostMapping("/goods/pictures/{id}")
    public JSONObject uploadGoodsPictures(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        GoodsPicInfo goodsPicInfo = pictureService.save(id, multipartFile);
        return RespBuilder.create(goodsPicInfo, VerifyRule.NOT_NULL);
    }

    @DeleteMapping("/goods/pictures/{id}")
    public JSONObject deleteGoodsPictures(@PathVariable("id") Integer id, @RequestBody GoodsPicInfo info, HttpSession session) {
        GoodsPicInfo goodsPicInfo = pictureService.delete(id, info.getUrl());
        return RespBuilder.create(goodsPicInfo, VerifyRule.NOT_NULL);
    }


    @GetMapping("/goods/{id}")
    public JSONObject getGoods(@PathVariable("id") Integer id, HttpSession session, @RequestBody JSONObject jsonObject) {
        StockedGoods goods = stockedGoodsService.getGoodsByPk(id);
        return RespBuilder.create(goods, VerifyRule.NOT_NULL);
    }

    @PostMapping("/goods")
    public JSONObject addGoods(@RequestBody StockedGoods goods, HttpSession session) {
        boolean res = stockedGoodsService.addGoods(goods);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }


    @GetMapping(
            value = "/goods/pictures/{url}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getGoodsPicture(@PathVariable("url") String url) {
        return pictureService.find(url);
    }


    @PutMapping("/goods")
    public JSONObject updateGoods(@RequestBody StockedGoods goods, HttpSession session) {
        boolean res = stockedGoodsService.updateGoods(goods);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @DeleteMapping("/goods/{id}")
    public JSONObject deleteGoods(@PathVariable("id") Integer id, HttpSession session) {
        boolean res = stockedGoodsService.deleteGoods(id);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }


    @GetMapping("/random_goods")
    public JSONObject getRandomGoods(@RequestParam(value = "category_id_list", required = true) List<Integer> categoryIdList) {
        List<StockedGoods> list = stockedGoodsService.getRandomGoods(categoryIdList);
        return RespBuilder.create(list, VerifyRule.COLLECTION_NOT_EMPTY);
    }


}
