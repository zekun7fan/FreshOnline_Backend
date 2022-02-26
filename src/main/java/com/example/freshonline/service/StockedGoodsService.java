package com.example.freshonline.service;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dao.SaledGoodsMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.dto.SearchParams;
import com.example.freshonline.dto.SearchResultInfo;
import com.example.freshonline.model.SaledGoodsExample;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.utils.PicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.freshonline.dao.GoodsCategoryMapper;
import com.example.freshonline.model.joined_tables.GoodsCategory;
import java.math.BigDecimal;
import java.util.*;

@Service
public class StockedGoodsService {


    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    @Autowired
    private SaledGoodsMapper saledGoodsMapper;

    final BigDecimal MIN_PRICE = BigDecimal.valueOf(0), MAX_PRICE = BigDecimal.valueOf(10000);
    
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;



    public GoodsCategory goodsDetails(Integer id) throws Exception{
        GoodsCategory gc = goodsCategoryMapper.selectByGoodsID(id);
        return gc;
    }




    /**
     * @author Josh Sun
     * @param param
     * @return
     *   goods_list: List<StockedGoods> goods_list
     *   brand_list: new ArrayList<>(Set<String> brand_set)
     *   price_range: List<BigDecimal> price_range: Arrays.asList(minInit: MAX_PRICE, maxInit: MIN_PRICE)
     *   goods_total: Integer goods_total
     */

    public JSONObject getSearch(SearchParams param){

        List<StockedGoods> stockedGoods = stockedGoodsMapper.selectByFilter(param);
        SearchResultInfo info = stockedGoodsMapper.searchInfo(param);
        System.out.println(info);
        List<BigDecimal> price_range = null;
        Integer goods_total = 0;
        List<String> brand_list = null;
        if (info.getGoods_total() != 0){
            price_range = new ArrayList<>(Arrays.asList(info.getMin_price(), info.getMax_price()));
            goods_total = info.getGoods_total();
            brand_list = Arrays.asList(info.getBrand_list().split(","));
        }

        JSONObject output = new JSONObject();
        output.put("goods_list", stockedGoods);
        output.put("price_range", price_range);
        output.put("brand_list", brand_list);
        output.put("goods_total", goods_total);
        output.put("page", param.getPage());

        return output;
    }
    /**
     * @author Huang
     */
    public List<StockedGoods> getWeeklySpecial(){
        return this.stockedGoodsMapper.selectByOnsale();
    }
    /**
     * @author Huang
     */
    public List<StockedGoods> getRandomGoods(List<Integer> categoryIdList){
        return this.stockedGoodsMapper.selectByCatogary(categoryIdList);
    }

    public StockedGoods getGoodsByPk(Integer Id){
        return this.stockedGoodsMapper.selectByPrimaryKey(Id);
    }



    public boolean addGoods(StockedGoods goods){
        Integer dummyId = goods.getId();
        goods.setId(null);
        if (stockedGoodsMapper.insertSelective(goods) != 1){
            return false;
        }
        Integer id = goods.getId();
        String pic = goods.getPic();
        String new_pic = PicUtils.change(dummyId, id, pic);
        goods.setPic(new_pic);
        return stockedGoodsMapper.updateByPrimaryKeySelective(goods)==1;
    }


    public boolean updateGoods(StockedGoods goods){
        return stockedGoodsMapper.updateByPrimaryKeySelective(goods)== 1;
    }

    public boolean deleteGoods(Integer id){
        SaledGoodsExample saledGoodsExample = new SaledGoodsExample();
        saledGoodsExample.createCriteria().andGoodsIdEqualTo(id);
        long num = saledGoodsMapper.countByExample(saledGoodsExample);
        // delete related pic
        boolean picDeleted = PicUtils.deleteAllPicByGoodsId(id);
        if (num > 0){
            // mark goods off the shelf, but not delete from db
            StockedGoods goods = new StockedGoods();
            goods.setId(id);
            goods.setActive(false);
            return picDeleted && stockedGoodsMapper.updateByPrimaryKeySelective(goods)==1;
        }else{
            // delete goods from stocked_goods table
            return picDeleted && stockedGoodsMapper.deleteByPrimaryKey(id)==1;
        }
    }

}
