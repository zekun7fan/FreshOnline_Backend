package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.model.StockedGoods;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class StockedGoodsControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    @Nested
    class TestNestPart1{

        public void init() {
            StockedGoods stockedGoods =
                        new StockedGoods((Integer) null, "chicken", (byte)1, new BigDecimal(1.0), new BigDecimal(1.0), new BigDecimal(1.0), (byte) 1, new BigDecimal(1.0),new BigDecimal(1.0), 3, "brand", 1, (byte)1, "String pic", true, "String descriptio");
            stockedGoodsMapper.insertSelective(stockedGoods);
        }

        @Test
        @Transactional
        @Rollback
        void getWeeklySpecial() throws Exception {
            init();
            MvcResult result = mockMvc.perform(get("/weekly_special")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0,obj.get("code"));
            Assertions.assertTrue(((JSONArray)obj.get("data")).size()>=1);
        }
        @Test
        @Transactional
        @Rollback
        void getRandomGoods() throws Exception {
            init();
            MvcResult result = mockMvc.perform(get("/random_goods?catogory_id_list=[1]")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0,obj.get("code"));
            Assertions.assertTrue(((JSONArray)obj.get("data")).size()>=1);
        }

    }

    @Test
    void getGoodsDetails() {
    }

    @Test
    void getSearch() {
    }

    @Test
    void uploadGoodsPictures() {
    }

    @Test
    void deleteGoodsPictures() {
    }

    @Test
    void addGoods() {
    }

    @Test
    void updateGoods() {
    }

    @Test
    void deleteGoods() {
    }

}