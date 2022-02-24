package com.example.freshonline.service;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.FreshOnlineApplication;
import com.example.freshonline.dto.SearchParams;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FreshOnlineApplication.class})
class StockedGoodsServiceTests {

    @Autowired
    private StockedGoodsService stockedGoodsService;

    /**
     * @author Josh Sun
     */
    @Test
    public void testGetSearch() {
        SearchParams param = new SearchParams();
        param.setCategory_id("(123,124)");
        param.setPage(1);
        param.setBrands("('tnt','abc')");
        JSONObject json = stockedGoodsService.getSearch(param);
        System.out.println("testGetSearch: " + json);
    }
}