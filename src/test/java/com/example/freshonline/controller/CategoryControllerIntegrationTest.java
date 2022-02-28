package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
import com.example.freshonline.dao.CategoryMapper;
import com.example.freshonline.dto.CategoryTreeNode;
import com.example.freshonline.model.Category;
import com.example.freshonline.model.CategoryExample;
import com.example.freshonline.utils.RespChecker;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryMapper categoryMapper;

    private String baseUrl = "http://localhost:8080";

    @Test
    void getCategoryTree() {


    }

    @Test
    void addCategory() throws Exception {
        // arrange
        String name = UUID.randomUUID().toString();
        Category category = new Category();
        category.setName(name);
        category.setLevel(1);
        category.setParentId(0);
        String requestStr = JSONObject.toJSONString(category);

        //act
        MvcResult result = mockMvc.perform(
                post(baseUrl + "/category")
                        .content(requestStr)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        String resp = result.getResponse().getContentAsString();

        //assert
        // check response
        Integer code = RespChecker.getCode(resp);
        assertEquals(Constants.SUCCESS_CODE, code);
        String msg = RespChecker.getMsg(resp);
        assertEquals(Constants.OPERATE_SUCCESS, msg);
        Boolean res = RespChecker.getDataAsPrimitiveType(resp, Boolean.class);
        assertTrue(res);

        // check database
        CategoryExample example = new CategoryExample();
        example.createCriteria().andNameEqualTo(name);
        List<Category> categories = categoryMapper.selectByExample(example);
        assertEquals(1, categories.size());

        // clean up
        int num = categoryMapper.deleteByPrimaryKey(categories.get(0).getId());
        assertEquals(1, num);


    }


    @Test
    void delCategory() throws Exception {

        // arrange
        String name = UUID.randomUUID().toString();
        Category category = new Category();
        category.setName(name);
        category.setLevel(1);
        category.setParentId(0);
        categoryMapper.insert(category);
        String requestStr = JSONObject.toJSONString(category);

        //act
        MvcResult result = mockMvc.perform(delete(baseUrl + "/category")
                        .content(requestStr)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        String resp = result.getResponse().getContentAsString();

        //assert
        // check response
        Integer code = RespChecker.getCode(resp);
        assertEquals(Constants.SUCCESS_CODE, code);
        String msg = RespChecker.getMsg(resp);
        assertEquals(Constants.OPERATE_SUCCESS, msg);
        List<CategoryTreeNode> res = RespChecker.getDataAsArray(resp, CategoryTreeNode.class);
        assertEquals(0, res.size());

        // check database
        CategoryExample example = new CategoryExample();
        example.createCriteria().andNameEqualTo(name);
        List<Category> categories = categoryMapper.selectByExample(example);
        assertEquals(0, categories.size());

    }
}