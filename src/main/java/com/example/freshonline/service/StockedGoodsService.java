package com.example.freshonline.service;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dao.SaledGoodsMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.dto.SearchParams;
import com.example.freshonline.dto.SearchResultInfo;
import com.example.freshonline.exception.CheckoutExcpetion;
import com.example.freshonline.exception.CustomException;
import com.example.freshonline.exception.RedisException;
import com.example.freshonline.model.Cart;
import com.example.freshonline.model.SaledGoodsExample;
import com.example.freshonline.model.StockedGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.freshonline.dao.GoodsCategoryMapper;
import com.example.freshonline.model.joined_tables.GoodsCategory;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class StockedGoodsService {


    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    @Autowired
    private SaledGoodsMapper saledGoodsMapper;
    
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private GoodsPictureService pictureService;

    @Autowired
    private RedisTemplate redisTemplate;

    public GoodsCategory goodsDetails(Integer id) throws Exception{
        GoodsCategory gc = goodsCategoryMapper.selectByGoodsID(id);
        return gc;
    }

    public GoodsCategory goodsDetailsRedis(Integer goods_id){
        GoodsCategory gc = (GoodsCategory)redisTemplate.opsForValue().get("goods"+goods_id);
        if (gc == null){
            System.out.println("not in redis");
            synchronized(this.getClass()){
                // search in redis again
                gc = (GoodsCategory)redisTemplate.opsForValue().get("goods"+goods_id);
                if (gc == null){
                    System.out.println("fail again in redis, go to database");
                    gc = goodsCategoryMapper.selectByGoodsID(goods_id);
                    redisTemplate.opsForValue().set("goods"+goods_id, gc);
                    redisTemplate.expire("goods"+goods_id, 60*60, TimeUnit.SECONDS);
                }
                System.out.println("in redis");
            }
        }else{
            System.out.println("in redis");
        }
        return gc;
    }

    public boolean updateGoodsRedis(StockedGoods goods) throws Exception{
        // 延迟双删
        redisTemplate.delete("goods"+goods.getId());

        if (stockedGoodsMapper.updateByPrimaryKeySelective(goods)!=1){
            throw new Exception();
        };
        Thread.sleep(100);
        redisTemplate.delete("goods"+goods.getId());
        return true;
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
        return this.stockedGoodsMapper.selectByCategory(categoryIdList);
    }

    public StockedGoods getGoodsByPk(Integer Id){
        return this.stockedGoodsMapper.selectByPrimaryKey(Id);
    }



    public boolean addGoods(StockedGoods goods){
        return stockedGoodsMapper.insertSelective(goods) == 1;
    }


    public boolean updateGoods(StockedGoods goods){
        return stockedGoodsMapper.updateByPrimaryKeySelective(goods)== 1;
    }

    public boolean deleteGoods(Integer id){
        SaledGoodsExample saledGoodsExample = new SaledGoodsExample();
        saledGoodsExample.createCriteria().andGoodsIdEqualTo(id);
        long num = saledGoodsMapper.countByExample(saledGoodsExample);
        // delete related pic
        pictureService.delete(id);
        if (num > 0){
            // mark goods off the shelf, but not delete from db
            StockedGoods goods = new StockedGoods();
            goods.setId(id);
            goods.setActive(false);
            return stockedGoodsMapper.updateByPrimaryKeySelective(goods)==1;
        }else{
            // delete goods from stocked_goods table
            return stockedGoodsMapper.deleteByPrimaryKey(id)==1;
        }
    }

    @Transactional
    public Integer decreaseStorage(List<Cart> Cart_entries) throws CheckoutExcpetion{
        List<Integer> invalid_Ids = new ArrayList<>();
        for (Cart c : Cart_entries){
            redisTemplate.delete("goods"+c.getGoodsId());
            try{
                stockedGoodsMapper.reduceStorage(c.getGoodsId(), c.getCount());
            }catch(org.springframework.dao.DataIntegrityViolationException e ){
                invalid_Ids.add(c.getGoodsId());
            }
        }
        // if invalid entries happens, signal which goods are incorrect (usually insufficient storage)
        if (invalid_Ids.size()>0){
            throw new CheckoutExcpetion(invalid_Ids);
        }
        // wait 100seconds
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // delete from redis again for data consistency
        for (Cart c : Cart_entries){
            redisTemplate.delete("goods"+c.getGoodsId());
        }
        
        return 0;
    }



    

}
