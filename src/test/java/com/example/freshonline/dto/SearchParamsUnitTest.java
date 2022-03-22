package com.example.freshonline.dto;


import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SearchParamsUnitTest {
//    @Autowired
//    private MockMvc mockMvc;

    @Test
    public void TestSearchParam() {
        SearchParams params = new SearchParams(
                0, 0, Arrays.asList("TNT", "ABC"), 0,
                "beef", 1, Arrays.asList(123, 124), 20, 0, 20);
        Assert.assertEquals(params.getPrice_low(), 0);
        Assert.assertEquals(Arrays.asList("TNT", "ABC"), params.getBrands());
        Assert.assertEquals(Arrays.asList(123, 124), params.getCategory_id());
    }

}