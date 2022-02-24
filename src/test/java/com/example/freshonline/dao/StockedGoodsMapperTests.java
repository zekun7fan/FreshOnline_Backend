package com.example.freshonline.dao;

import com.example.freshonline.FreshOnlineApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FreshOnlineApplication.class})
class StockedGoodsMapperTests {

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    @Test
    public void testSelectByFilter() {
    }

    @Test
    public void testSearchInfo() {
    }
}