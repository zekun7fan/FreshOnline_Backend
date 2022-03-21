package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dto.SearchParams;
import com.example.freshonline.service.StockedGoodsService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StockedGoodsControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockedGoodsService stockedGoodsService;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestNestPart2 {

        @ParameterizedTest
        @ValueSource( strings = {
                        "/goods?price_low=1&price_high=30&brands=&sort_type=0&keyword=&page=1&category_id=&num_per_page=20",
                        "/goods?price_low=5&price_high=100&brands=OPQ&sort_type=0&keyword=&page=1&category_id=&num_per_page=20",
                        "/goods?brands=ABC,TNT&price_low=0&price_high=1000&sort_type=0&keyword=&page=1&category_id=&num_per_page=20",
                        "/goods?price_low=1&price_high=30&category_id=123&brands=&sort_type=0&keyword=&page=1&num_per_page=20",
                        "/goods?category_id=123,124&price_low=0&price_high=1000&brands=&sort_type=0&keyword=&page=1&num_per_page=20",
                        "/goods?price_low=5&price_high=100&brands=&page=3&sort_type=0&keyword=&category_id=&num_per_page=20",
                })
        public void getSearch(String url) throws Exception {
            JSONObject outputJSON = new JSONObject();
            outputJSON.put("mockedKey", "mockedValue");

            Mockito.when(stockedGoodsService.getSearch(Mockito.any(SearchParams.class))).thenReturn(outputJSON);
            MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();

            System.out.println("response type: " + mvcResult.getResponse().getContentType());
            System.out.println("response: " + mvcResult.getResponse().getContentAsString());
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            JSONObject data = (JSONObject) resp.get("data");
            Assert.assertEquals(0, resp.get("code"));
            Assert.assertEquals(outputJSON, data);
        }


//        @ParameterizedTest
//        @ValueSource( strings = {
//                "/goods?price_low=abc"
//        })
//        public void getSearchwithException(String url) {
//            try {
//                MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();
//
//                System.out.println("response type: " + mvcResult.getResponse().getContentType());
//                System.out.println("response: " + mvcResult.getResponse().getContentAsString());
//            } catch (Exception e){
//                System.out.println("exception message: " + e.getMessage());
//            }
//        }

    }

}
