package com.example.freshonline.controller;



import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSON;

import com.example.freshonline.constants.Constants;

import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.service.GoodsService;
import com.example.freshonline.utils.RespBuilder;
import com.example.freshonline.utils.ValidationChecker;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.example.freshonline.model.joined_tables.GoodsCategory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Josh Sun
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goodsdetails/{goods_id}")
    public JSONObject getGoodsDetails(@PathVariable("goods_id") String id) {
        JSONObject res = new JSONObject();
        try{
            Integer goods_id = Integer.parseInt(id);
            GoodsCategory gc = goodsService.goodsDetails(goods_id);
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
     * @param price_low_req
     * @param price_high_req
     * @param brands
     * @param sort_type_req
     * @param keyword
     * @param page_req
     * @param category_id_req
     * @return
     */
    @GetMapping("/goods")
    public JSONObject getSearch(@RequestParam(value = "price_low", required = false) String price_low_req,
                                @RequestParam(value = "price_high", required = false) String price_high_req,
                                @RequestParam(value = "brands", required = false) String brands,
                                @RequestParam(value = "sort_type", required = false) String sort_type_req,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "page", required = false) String page_req,
                                @RequestParam(value = "category_id", required = false) String category_id_req){

        JSONObject param = new JSONObject();
        ValidationChecker vc = new ValidationChecker();
        param.put("page", vc.str2int(page_req, 1));
        if (price_low_req != null) param.put("price_low", vc.str2int(price_low_req, 0));
        if (price_high_req != null) param.put("price_high", vc.str2int(price_high_req, 10000));
        /**
         * brands:brand1, brand2 逗号分隔
         */
        if (brands != null) param.put("brands", brands);
        if (keyword != null) param.put("keyword", keyword);
        if ( (sort_type_req != null) && (vc.str2int(sort_type_req, 0) != 0) ){
            param.put("sort_type", vc.str2int(sort_type_req, 0));
        }
        if ( (category_id_req != null) && (vc.str2int(category_id_req, 0) != 0) ){
            param.put("category_id", vc.str2int(category_id_req, 0));
        }

        GoodsService gs = new GoodsService();
        JSONObject output = gs.getSearch(param);

        return RespBuilder.create(output, VerifyRule.NOT_NULL, Constants.OPERATE_SUCCESS, Constants.OPERATE_FAIL);
    }


    /**
     * @author Huang
     */
    @GetMapping("/weekly_special")
    public JSONObject getWeeklySpecial(){
        return RespBuilder.create(this.goodsService.getWeeklySpecial(), VerifyRule.COLLECTION_NOT_EMPTY,"success","fail");
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
        return RespBuilder.create(this.goodsService.getRandomGoods(Idlist), VerifyRule.COLLECTION_NOT_EMPTY,"success","fail");
    }

//    /**
//     * @author Josh Sun
//     */
//    @GetMapping("*")
//    public void defaultMappingTest(){
//        System.out.println("default mapping");
//    }

}
