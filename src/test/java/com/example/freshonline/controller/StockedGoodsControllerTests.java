package com.example.freshonline.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.FreshOnlineApplication;
import com.example.freshonline.model.StockedGoods;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                "http://localhost:8080/goods",
                "http://localhost:8080/goods?"
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
