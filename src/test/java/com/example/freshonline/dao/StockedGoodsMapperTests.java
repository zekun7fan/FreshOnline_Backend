package com.example.freshonline.dao;

import com.example.freshonline.FreshOnlineApplication;
import com.example.freshonline.dto.SearchParams;
import com.example.freshonline.dto.SearchResultInfo;
import com.example.freshonline.model.StockedGoods;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FreshOnlineApplication.class})
class StockedGoodsMapperTests {

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    @Test
    public void testSelectByFilter() {
        SearchParams param = new SearchParams();
        param.setCategory_id("(123,124)");
        param.setPage(1);
        param.setBrands("('tnt','abc')");
        List<StockedGoods> goods = stockedGoodsMapper.selectByFilter(param);
        for (StockedGoods good : goods){
            System.out.println(good);
        }
    }

    @Test
    public void testSearchInfo() {
        SearchParams param = new SearchParams();
        param.setCategory_id("(123,124)");
        param.setPage(1);
        param.setBrands("('tnt','abc')");
        SearchResultInfo info = stockedGoodsMapper.searchInfo(param);
        System.out.println(info);
    }
}