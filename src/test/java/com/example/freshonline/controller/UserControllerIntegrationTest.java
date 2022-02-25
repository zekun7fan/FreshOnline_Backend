package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dao.OrderMapper;
import com.example.freshonline.dao.SaledGoodsMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.dao.UserMapper;
import com.example.freshonline.dto.OrderDetail;
import com.example.freshonline.model.Order;
import com.example.freshonline.model.SaledGoods;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    @Nested
    class TestNestPart1{

        @Autowired
        StockedGoodsMapper stockedGoodsMapper;
        @Autowired
        SaledGoodsMapper saledGoodsMapper;
        @Autowired
        OrderMapper orderMapper;

        private User user;

        void init(){
            User user = new User((Integer)null, "name", "password", "email", (byte)1, "location");
            userMapper.insert(user);
            this.user = user;
        }
        void prepareOrder(){
            Order order = new Order((Integer) null, user.getId(), 1, LocalDateTime.parse("2022-02-22T22:22:22"), LocalDateTime.parse("2022-02-22T22:22:22"), LocalDateTime.parse("2022-02-22T22:22:22"), (byte)1, "location");
            orderMapper.insert(order);
            StockedGoods stockedGoods =
                    new StockedGoods((Integer) null, "chicken", (byte)1, new BigDecimal(1.0), new BigDecimal(1.0), new BigDecimal(1.0), (byte) 1, new BigDecimal(1.0),new BigDecimal(1.0), 3, "brand", 1, (byte)1, "String pic", true, "String descriptio");
            stockedGoodsMapper.insertSelective(stockedGoods);
            SaledGoods saledGoods =new SaledGoods(order.getOrderId(), stockedGoods.getId(), BigDecimal.valueOf(1.0), BigDecimal.valueOf(1.0), 3, "comment");
            saledGoodsMapper.insert(saledGoods);
            }

        @Test
        @Transactional
        @Rollback
        void getUserInfo() throws Exception {
            init();
            int userId = user.getId();
            MvcResult result = mockMvc.perform(get("/user?user_id="+userId)
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0,obj.get("code"));
            JSONObject user = obj.getJSONObject("data");
            for(String key:user.keySet()) {
                Assertions.assertNotNull(user.get(key));
            }
        }

        @Test
        @Transactional
        @Rollback
        void updateUserPassword() throws Exception {
            init();
            MvcResult result = mockMvc.perform(get("/user/update_password?user_id="+user.getId()
            +"&old_pass=wrongpass"+"&new_pass=newpass")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertNotEquals(0,obj.get("code"));

            result = mockMvc.perform(get("/user/update_password?user_id="+user.getId()
                    +"&old_pass="+user.getPassword()+"&new_pass=newPass")
                    .content("")).andReturn();
            response = result.getResponse();
            obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0,obj.get("code"));
            User newUser = userMapper.selectByPrimaryKey(user.getId());
            Assertions.assertEquals("newPass",newUser.getPassword());
        }

        @Test
        @Transactional
        @Rollback
        void updateUserAddress() throws Exception {
            init();
            MvcResult result = mockMvc.perform(get("/user/update_address?user_id="+user.getId()
                    +"&address=newAddress")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0,obj.get("code"));
            User newUser = userMapper.selectByPrimaryKey(user.getId());
            Assertions.assertEquals("newAddress",newUser.getLocation());
        }

        @Test
        @Transactional
        @Rollback
        void searchUserOrders() throws Exception {
            init();
            prepareOrder();
            MvcResult result = mockMvc.perform(get("/user/orders?user_id="+user.getId()+"&position=0")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0,obj.get("code"));
            JSONArray data = ((JSONArray)obj.get("data"));
            Assertions.assertTrue(data.size()>=1);
            OrderDetail orderDetail = (OrderDetail) data.getJSONObject(0).toJavaObject(OrderDetail.class);
            Assertions.assertTrue(orderDetail.getGoodsList().size()>=1);
            Assertions.assertNotNull(orderDetail.getOrderId());
            Assertions.assertNotNull(orderDetail.getGoodsList().get(0).getName());
        }

    }

    @Test
    void login() {
    }

    @Test
    void register() {
    }
}