package com.example.freshonline.controller;



import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
import com.example.freshonline.dto.GoodsPicInfo;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.service.StockedGoodsService;
import com.example.freshonline.utils.PicUtils;
import com.example.freshonline.utils.RespBuilder;
import com.example.freshonline.utils.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Josh Sun
 */
@RestController
public class StockedGoodsController {


    @Autowired
    private StockedGoodsService stockedGoodsService;


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
         * brands: ('brand1', 'brand2')
         * @TODO: 如何前端检验，是否需要后端检验
         */
        if (brands != null) param.put("brands", brands);
        if (keyword != null) param.put("keyword", keyword);
        if ( (sort_type_req != null) && (vc.str2int(sort_type_req, 0) != 0) ){
            param.put("sort_type", vc.str2int(sort_type_req, 0));
        }
        if ( (category_id_req != null) && (vc.str2int(category_id_req, 0) != 0) ){
            param.put("category_id", vc.str2int(category_id_req, 0));
        }

        StockedGoodsService gs = new StockedGoodsService();
        List<StockedGoods> output_by_service = gs.getSearch(param);

        JSONObject output = new JSONObject();
        String s = JSONObject.toJSONString(output_by_service);
        System.out.println(s);
        output.put("Result", s);
        return output;
    }

    /**
     * @author Josh Sun
     */
    @GetMapping("*")
    public void defaultMappingTest(){
        System.out.println("default mapping");
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


}
