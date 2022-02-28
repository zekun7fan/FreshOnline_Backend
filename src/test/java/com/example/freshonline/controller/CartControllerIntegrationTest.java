package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dao.CartMapper;
import com.example.freshonline.dao.CategoryMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.dao.UserMapper;
import com.example.freshonline.model.Cart;
import com.example.freshonline.model.Category;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.model.User;
import com.example.freshonline.service.CartService;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import org.springframework.http.MediaType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// TODO: proper exception states

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CartService cartService;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestNestPart1 {
        private StockedGoods stockedGoods1;
        private StockedGoods stockedGoods2;
        private User user;

        @BeforeAll
        public void setup() {

            this.stockedGoods1 = new StockedGoods((Integer) null, "testproduct", (byte) 1, new BigDecimal(9.99),
                    new BigDecimal(1000), new BigDecimal(100), (byte) 0, (BigDecimal) null, new BigDecimal(3.5), 10,
                    "brandname1", 0, (byte) 1, "String pic", true, "String descriptio");
            stockedGoodsMapper.insertSelective(stockedGoods1);
            this.stockedGoods2 = new StockedGoods((Integer) null, "testproduct2", (byte) 1, new BigDecimal(9.99),
                    new BigDecimal(1000), new BigDecimal(100), (byte) 0, (BigDecimal) null, new BigDecimal(3.5), 10,
                    "brandname1", 0, (byte) 1, "String pic", true, "String descriptio");
            stockedGoodsMapper.insertSelective(stockedGoods2);
            this.user = new User((Integer) null, "name", "password", "email", (byte) 1, "location");
            userMapper.insertSelective(user);

            cartMapper.insertSelective(new Cart(user.getId(), stockedGoods1.getId(), new BigDecimal(1)));
            // cartMapper.insertSelective(new Cart(user.getId(), stockedGoods2.getId(), new
            // BigDecimal(1)));
        }

        @Test
        public void getCartGoodSuccess() throws Exception {
            // assert success
            MvcResult mvcResult = mockMvc
                    .perform(get(
                            "/cart_element?user_id=" + this.user.getId().toString() + "&goods_id="
                                    + this.stockedGoods1.getId().toString()))
                    .andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            JSONObject data = (JSONObject) resp.get("data");
            Assertions.assertEquals(0, resp.get("code"));
            // Assertions.assertEquals(stockedGoods1.getId(), data.get("id"));
            // Assertions.assertEquals(cate3.getId(), ((JSONObject)
            // data.get("cate3")).get("id"));
        }

        @Test
        public void getCartGoodBadInput() throws Exception {
            MvcResult mvcResult = mockMvc.perform(get("/cart_element?user_id=addasddsdas")).andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertNull(resp);
        }

        @Test
        public void getCartGoodsSuccess() throws Exception {
            MvcResult mvcResult = mockMvc.perform(get("/cart?user_id=" + this.user.getId())).andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertEquals(0, resp.get("code"));
            Assertions.assertTrue(((JSONArray) resp.get("data")).size() == 1);
            // Assertions.assertEquals(stockedGoods1.getId(), data.get("id"));
            // Assertions.assertEquals(cate3.getId(), ((JSONObject)
            // data.get("cate3")).get("id"));
        }

        @Test
        public void getCartGoodsBadInput() throws Exception {
            MvcResult mvcResult = mockMvc.perform(get("/cart?user_id=addasddsdas")).andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertNotEquals(0, resp.get("code"));
        }

        @Test
        public void addCartEntrySuccess() throws Exception {

            Cart cart = new Cart(user.getId(), stockedGoods2.getId(), new BigDecimal(1));
            String requestStr = JSONObject.toJSONString(cart);

            // act
            MvcResult mvcResult = mockMvc.perform(
                    put("/cart")
                            .content(requestStr)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertEquals(0, resp.get("code"));
            List<Cart> cart_goods = cartService.getCart(user.getId());
            Assertions.assertEquals(2,cart_goods.size());
        }

        @Test
        public void addCartEntryBadInput() throws Exception {

            Cart cart = new Cart(user.getId(), stockedGoods1.getId(), new BigDecimal(1));
            String requestStr = JSONObject.toJSONString(cart);

            // act
            MvcResult mvcResult = mockMvc.perform(
                    put("/cart")
                            .content(requestStr)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertNotEquals(0, resp.get("code"));
        }

        @Test
        public void updateCartEntrySuccess() throws Exception {

            Cart cart = new Cart(user.getId(), stockedGoods1.getId(), new BigDecimal(2));
            String requestStr = JSONObject.toJSONString(cart);

            // act
            MvcResult mvcResult = mockMvc.perform(
                    post("/cart")
                            .content(requestStr)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertEquals(0, resp.get("code"));
        }

        @Test
        public void updateCartEntryBadInput() throws Exception {

            String requestStr = JSONObject.toJSONString(null);

            // act
            MvcResult mvcResult = mockMvc.perform(
                    post("/cart")
                            .content(requestStr)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertNull(resp);
        }


        @Test
        public void deleteCartEntrySuccess() throws Exception {

            Cart cart = new Cart(user.getId(), stockedGoods2.getId(), new BigDecimal(1));
            String requestStr = JSONObject.toJSONString(cart);

            // act
            MvcResult mvcResult = mockMvc.perform(
                    delete("/cart")
                            .content(requestStr)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertEquals(0, resp.get("code"));
            List<Cart> cart_goods = cartService.getCart(user.getId());
            Assertions.assertEquals(1,cart_goods.size());
        }

        @Test
        public void deleteCartEntryBadInput() throws Exception {

            String requestStr = JSONObject.toJSONString(null);

            // act
            MvcResult mvcResult = mockMvc.perform(
                    delete("/cart")
                            .content(requestStr)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertNull(resp);
        }


        @AfterAll
        public void finish() {
            stockedGoodsMapper.deleteByPrimaryKey(stockedGoods1.getId());
            stockedGoodsMapper.deleteByPrimaryKey(stockedGoods2.getId());
            userMapper.deleteByPrimaryKey(user.getId());
        }
    }
}