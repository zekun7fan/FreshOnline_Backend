package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dto.SearchParams;
import com.example.freshonline.service.StockedGoodsService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

        @Test
        public void getSearch() throws Exception {
            String url = "/goods?price_low=1&price_high=30&brands=&sort_type=0&keyword=&page=1&category_id=&num_per_page=20";
            SearchParams mockedParams = mock(SearchParams.class);
            mockedParams.setPrice_low(1);
            mockedParams.setPrice_high(30);
            JSONObject mockedJSON = mock(JSONObject.class);
            mockedJSON.put("mockedKey", "mockedValue");
            Mockito.when(stockedGoodsService.getSearch(mockedParams)).thenReturn(mockedJSON);
            MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();
            System.out.println("response type: " + mvcResult.getResponse().getContentType());
            System.out.println("response: " + mvcResult.getResponse().getContentAsString());
        }
    }

}
