package com.example.freshonline.controller;

import com.example.freshonline.dao.FavoriteMapper;
import com.example.freshonline.dao.HelloWorldMapper;
import com.example.freshonline.dto.HelloWorldDto;
import com.example.freshonline.service.FavouriteService;
import com.example.freshonline.service.HelloWorldService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebMvcTest({FavouriteController.class, FavouriteService.class, FavoriteMapper.class})
//@DataJdbcTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class HelloWorldControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    void m1() throws Exception {
        HelloWorldDto dto = new HelloWorldDto("ss", 55, "66");
        String expected_res = "123456";

        MvcResult result = mockMvc.perform(post("/helloworld")
                .content(dto.toString())
        ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println("d");

    }

    @Test
    void m2() throws Exception {
        MvcResult result = mockMvc.perform(get("/favourite?user_id=5&goods_id=3"))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println("d");
    }

}