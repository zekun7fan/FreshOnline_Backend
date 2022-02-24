package com.example.freshonline.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.FreshOnlineApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


/**
 * How to run junit test?
 * run with configuration, select junit and class
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FreshOnlineApplication.class})
@AutoConfigureMockMvc
public class StockedGoodsControllerTests {

    @Autowired
    private StockedGoodsController stockedGoodsController;
    @Autowired
    private MockMvc mockMvc;

    /**
     * mockMvc: lib for testing controller, simulate web requests
     */

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(stockedGoodsController).build();
    }

    /**
     * @author Josh Sun
     * @throws Exception
     */
    @Test
    public void testGetSearch() throws Exception{
        String[] urls = new String[]{
                "http://localhost:8080/goods", "http://localhost:8080/goods?",
                "http://localhost:8080/goods?min_price=3",
                "http://localhost:8080/goods?min_price=-1",
                "http://localhost:8080/goods?min_price=-9.99",
                "http://localhost:8080/goods?min_price=1000",
                "http://localhost:8080/goods?max_price=20",
                "http://localhost:8080/goods?max_price=-1.98",
                "http://localhost:8080/goods?min_price=-9.99&max_price=-2.9",
                "http://localhost:8080/goods?sort_type=0",
                "http://localhost:8080/goods?sort_type=1",
                "http://localhost:8080/goods?sort_type=5",
                "http://localhost:8080/goods?sort_type=-5.98%min_price=abc",
                "http://localhost:8080/goods?sort_type=-tnt%min_price=abc",
                "http://localhost:8080/goods?category_id=shu",
                "http://localhost:8080/goods?category_id=123,124",
                "http://localhost:8080/goods?category_id=125&keyword=abc",
                "http://localhost:8080/goods?category_id=125&page=-1",
                "http://localhost:8080/goods?category_id=125&page=5252.32",
                "http://localhost:8080/goods?brands=abc.tnt&keyword=beef",
        }; // some test urls here
        for (String url : urls){
            System.out.println("testing getSearch, test url = " + url);
            MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();
            System.out.println("response type: " + mvcResult.getResponse().getContentType());
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assert.assertNotNull(resp);
            System.out.println("resp: " + resp);
            System.out.println("data:" + resp.get("data"));
            System.out.println("test finish, url = " + url);
        }
    }
}
