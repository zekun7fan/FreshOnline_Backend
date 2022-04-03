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
import java.util.HashMap;
import java.util.List;


/**
 * @author Josh Sun
 */
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
        System.out.println(checkOutInfo.getLocation());
        JSONObject res = new JSONObject();
        List<Cart> clist = cartService.getCart(checkOutInfo.getId());
        // try to decrement the storage count in the database
        try {
            stockedGoodsService.decreaseStorage(clist);
        } catch (CheckoutExcpetion e) {
            // if failed return list of goods id cannot be purchased
            res.put("code", 1);
            res.put("msg", e.getErrorGoodsList().toString());
            return res ;
        }
        // send the event to event listener
        HashMap<Integer,BigDecimal> map = new HashMap<Integer,BigDecimal>();
        for(Cart c:clist){
            map.put(c.getGoodsId(),c.getCount());
        }
        CreateOrderDetail detail = new CreateOrderDetail(checkOutInfo.getId(), checkOutInfo.getLocation(), map);
        customEventPublisher.publishCreateOrderEvent(detail);
        res.put("code", 0);
        res.put("msg", "success");
        return res ;
    }





    @GetMapping("/goodsdetails/{goods_id}")
    public JSONObject getGoodsDetails(@PathVariable("goods_id") String id) {
        JSONObject res = new JSONObject();
        try{
            Integer goods_id = Integer.parseInt(id);
            GoodsCategory gc = stockedGoodsService.goodsDetails(goods_id);
            JSONObject data = (JSONObject) JSONObject.toJSON(gc);
            res.put("code", 0);
            res.put("data",data);
            return res;
        }
        catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            res.put("code", 1);
            res.put("msg", sw.toString());
            return res;
        }
    }


    /**
    * @author Josh Sun
    * @return
    */
   @GetMapping("/goods")
   public JSONObject getSearch(@Validated SearchParams param) {

       param.computePage();
       System.out.println(param);

       JSONObject output = stockedGoodsService.getSearch(param);
       return RespBuilder.create(output, VerifyRule.NOT_NULL, RespConstant.OPERATE_SUCCESS, RespConstant.OPERATE_FAIL);
   }


    /**
     * @author Huang
     */
    @GetMapping("/weekly_special")
    public JSONObject getWeeklySpecial(){
        return RespBuilder.create(stockedGoodsService.getWeeklySpecial(), VerifyRule.COLLECTION_NOT_EMPTY,"success","fail");
    }


    @PostMapping("/goods/picture/{id}")
    public JSONObject uploadGoodsPictures(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile multipartFile){
        GoodsPicInfo goodsPicInfo = pictureService.save(id, multipartFile);
        return RespBuilder.create(goodsPicInfo, VerifyRule.NOT_NULL);
    }

    @DeleteMapping("/goods/picture/{id}")
    public JSONObject deleteGoodsPictures(@PathVariable("id") Integer id, @RequestBody GoodsPicInfo info){
        GoodsPicInfo goodsPicInfo = pictureService.delete(id, info.getUrl());
        return RespBuilder.create(goodsPicInfo, VerifyRule.NOT_NULL);
    }


    @GetMapping("/goods/{id}")
    public JSONObject getGoods(@PathVariable("id") Integer id, HttpSession session, @RequestBody JSONObject jsonObject){
        StockedGoods goods = stockedGoodsService.getGoodsByPk(id);
        return RespBuilder.create(goods, VerifyRule.NOT_NULL);

    }

    @PostMapping("/goods")
    public JSONObject addGoods(@RequestBody StockedGoods goods){
        boolean res = stockedGoodsService.addGoods(goods);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }


    @GetMapping(
            value = "/goods/pictures/{id}/{path}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getGoodsPicture(@PathVariable("id") Integer id, @PathVariable("path") String path){
        StringBuilder sb = new StringBuilder();
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return null;
        }
        sb.append("http://").append(localHost.getHostAddress()).append(":8080").
                append("/goods/pictures").append("/").append(id).append("/").append(path);
        String url = sb.toString();
        return pictureService.find(url);
    }


    @PutMapping("/goods")
    public JSONObject updateGoods(@RequestBody StockedGoods goods){
        boolean res = stockedGoodsService.updateGoods(goods);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @DeleteMapping("/goods/{id}")
    public JSONObject deleteGoods(@PathVariable("id") Integer id){
        boolean res = stockedGoodsService.deleteGoods(id);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    /**
     * @author Huang
     */
    @GetMapping("/random_goods")
    public JSONObject getRandomGoods(@RequestParam(value = "catogory_id_list", required = true) List<Integer> categoryIdList){
//        if(categoryIdList.isEmpty()) Idlist.add(-1);
        return RespBuilder.create(stockedGoodsService.getRandomGoods(categoryIdList), VerifyRule.COLLECTION_NOT_EMPTY,"success","fail");
    }


}
