package com.example.freshonline.controller;



import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
import com.example.freshonline.dto.GoodsPicInfo;
import com.example.freshonline.dto.SearchParams;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.service.StockedGoodsService;
import com.example.freshonline.utils.PicUtils;
import com.example.freshonline.utils.RespBuilder;
import com.example.freshonline.utils.ValidationChecker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.freshonline.model.joined_tables.GoodsCategory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Josh Sun
 */
@RestController
public class StockedGoodsController {

    @Autowired
    private StockedGoodsService stockedGoodsService;

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
    private final GoodsService goodsService;
    public GoodsController(GoodsService goodsService){
        this.goodsService=goodsService;
    }

    /**
    * @author Josh Sun
    * @return
    */
   @GetMapping("/goods")
   public JSONObject getSearch(@RequestParam Map<String, String> map){

       SearchParams param = new SearchParams();
       param = param.verifyParams(map);
       System.out.println(param);

       JSONObject output = stockedGoodsService.getSearch(param);
       return RespBuilder.create(output, VerifyRule.NOT_NULL, Constants.OPERATE_SUCCESS, Constants.OPERATE_FAIL);
   }


    /**
     * @author Huang
     */
    @GetMapping("/weekly_special")
    public JSONObject getWeeklySpecial(){
        return RespBuilder.create(stockedGoodsService.getWeeklySpecial(), VerifyRule.COLLECTION_NOT_EMPTY,"success","fail");
    }


    @PostMapping("/goods/pic/{id}")
    public JSONObject uploadGoodsPictures(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile multipartFile){
        GoodsPicInfo info = PicUtils.save(id, multipartFile);
        return RespBuilder.create(info, VerifyRule.NOT_NULL, Constants.OPERATE_SUCCESS, Constants.OPERATE_FAIL);
    }

    @DeleteMapping("/goods/pic/{id}")
    public JSONObject deleteGoodsPictures(@PathVariable("id") Integer id, @RequestBody JSONObject jsonObject){
        String url = jsonObject.getString("url");
        GoodsPicInfo info = PicUtils.delete(id, url);
        return RespBuilder.create(info, VerifyRule.NOT_NULL, Constants.OPERATE_SUCCESS, Constants.OPERATE_FAIL);
    }

    @PostMapping("/goods")
    public JSONObject addGoods(@RequestBody JSONObject jsonObject){
        StockedGoods goods = jsonObject.toJavaObject(StockedGoods.class);
        boolean res = stockedGoodsService.addGoods(goods);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @PutMapping("/goods")
    public JSONObject updateGoods(@RequestBody JSONObject jsonObject){
        StockedGoods goods = jsonObject.toJavaObject(StockedGoods.class);
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
    public JSONObject getRandomGoods(@RequestParam(value = "catogory_id_list", required = true) List<String> categoryIdList){
        ArrayList<Integer> Idlist = new ArrayList<Integer>();
        for(String categoryId: categoryIdList){
            Integer CId = (new ValidationChecker()).str2int(categoryId,1);
            Idlist.add(CId);
        }
        if(Idlist.isEmpty()) Idlist.add(-1);
        return RespBuilder.create(stockedGoodsService.getRandomGoods(Idlist), VerifyRule.COLLECTION_NOT_EMPTY,"success","fail");
    }


}