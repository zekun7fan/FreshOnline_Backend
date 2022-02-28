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
import org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryMapper categoryMapper;

    private static String baseUrl = "http://localhost:8080";


    /**
     * create catogory with random name and specific level and parentId
     * @param level level
     * @param parentId parentId
     * @return catogory
     */
    private static Category createRandomCategry(Integer level, Integer parentId) {
        String name = UUID.randomUUID().toString();
        Category category = new Category();
        category.setName(name);
        category.setLevel(level != null ? level : 1);
        category.setParentId(parentId != null ? parentId : 1);
        return category;
    }


    @Test
    void getCategoryTree() throws Exception {
        // arrange
        Category c1 = createRandomCategry(1, 0);
        categoryMapper.insert(c1);
        Category c2 = createRandomCategry(c1.getLevel() + 1, c1.getId());
        categoryMapper.insert(c2);
        Category c3 = createRandomCategry(c1.getLevel() + 1, c1.getId());
        categoryMapper.insert(c3);
        Category c4 = createRandomCategry(c2.getLevel() + 1, c2.getId());
        categoryMapper.insert(c4);
        Category c5 = createRandomCategry(c3.getLevel() + 1, c3.getId());
        categoryMapper.insert(c5);

        //act
        MvcResult result = mockMvc.perform(get(baseUrl + "/categoryTree")).andReturn();

        // assert
        String resp = result.getResponse().getContentAsString();
        Integer code = RespChecker.getCode(resp);
        assertEquals(Constants.SUCCESS_CODE, code);
        String msg = RespChecker.getMsg(resp);
        assertEquals(Constants.OPERATE_SUCCESS, msg);
        List<CategoryTreeNode> data = RespChecker.getDataAsArray(resp, CategoryTreeNode.class);
        CategoryTreeNode n1 = null;
        for (CategoryTreeNode node: data){
            if (node.getId().equals(c1.getId())){
                n1 = node;
                break;
            }
        }
        // n1
        assertNotNull(n1);
        assertEquals(2, n1.getChildren().size());
        // n2
        CategoryTreeNode n2 = n1.getChildren().get(0);
        assertEquals(c2.getId(), n2.getId());
        assertEquals(1, n2.getChildren().size());

        //n3
        CategoryTreeNode n3 = n1.getChildren().get(1);
        assertEquals(c3.getId(), n3.getId());
        assertEquals(1, n3.getChildren().size());

        //n4
        CategoryTreeNode n4 = n2.getChildren().get(0);
        assertEquals(c4.getId(), n4.getId());
        assertEquals(0, n4.getChildren().size());

        //n5
        CategoryTreeNode n5 = n3.getChildren().get(0);
        assertEquals(c5.getId(), n5.getId());
        assertEquals(0, n5.getChildren().size());

        categoryMapper.deleteByPrimaryKey(c1.getId());
        categoryMapper.deleteByPrimaryKey(c2.getId());
        categoryMapper.deleteByPrimaryKey(c3.getId());
        categoryMapper.deleteByPrimaryKey(c4.getId());
        categoryMapper.deleteByPrimaryKey(c5.getId());
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