package com.example.freshonline.controller;



import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.RespConstant;
import com.example.freshonline.dto.GoodsPicInfo;
import com.example.freshonline.dto.SearchParams;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.service.StockedGoodsService;
import com.example.freshonline.utils.PicUtils;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.freshonline.model.joined_tables.GoodsCategory;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;


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
   public JSONObject getSearch(@Validated SearchParams param){

       param.convert();
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


    @PostMapping("/goods/pic/{id}")
    public JSONObject uploadGoodsPictures(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile multipartFile){
        GoodsPicInfo info = PicUtils.save(id, multipartFile);
        return RespBuilder.create(info, VerifyRule.NOT_NULL, RespConstant.OPERATE_SUCCESS, RespConstant.OPERATE_FAIL);
    }

    @DeleteMapping("/goods/pic/{id}")
    public JSONObject deleteGoodsPictures(@PathVariable("id") Integer id, @RequestBody JSONObject jsonObject){
        String url = jsonObject.getString("url");
        GoodsPicInfo info = PicUtils.delete(id, url);
        return RespBuilder.create(info, VerifyRule.NOT_NULL, RespConstant.OPERATE_SUCCESS, RespConstant.OPERATE_FAIL);
    }


    @DeleteMapping("/goods/pic/{id}/all")
    public JSONObject deleteGoodsPictures(@PathVariable("id") Integer id){
        boolean res = PicUtils.deleteAllPicByGoodsId(id);
        return RespBuilder.create(res, VerifyRule.TRUE, RespConstant.OPERATE_SUCCESS, RespConstant.OPERATE_FAIL);
    }


    @PreAuthorize("checkUserId(#id, #session, #jsonObject)")
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
