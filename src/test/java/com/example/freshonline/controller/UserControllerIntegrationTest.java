package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
import com.example.freshonline.dao.OrderMapper;
import com.example.freshonline.dao.SaledGoodsMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.dao.UserMapper;
import com.example.freshonline.dto.LoginedUserInfo;
import com.example.freshonline.dto.OrderDetail;
import com.example.freshonline.dto.UserJwtPayload;
import com.example.freshonline.model.*;
import com.example.freshonline.model.Order;
import com.example.freshonline.utils.JwtUtils;
import com.example.freshonline.utils.MockRequestSender;
import com.example.freshonline.utils.RespChecker;
import com.example.freshonline.utils.TimeUtils;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    class TestNestPart1 {

        @Autowired
        StockedGoodsMapper stockedGoodsMapper;
        @Autowired
        SaledGoodsMapper saledGoodsMapper;
        @Autowired
        OrderMapper orderMapper;

        private User user;

        void init() {
            User user = new User((Integer) null, "name", "password", "email", (byte) 1, "location");
            userMapper.insert(user);
            this.user = user;
        }

        void prepareOrder() {
            Order order = new Order((Integer) null, user.getId(), 1, LocalDateTime.parse("2022-02-22T22:22:22"), LocalDateTime.parse("2022-02-22T22:22:22"), LocalDateTime.parse("2022-02-22T22:22:22"), (byte) 1, "location");
            orderMapper.insert(order);
            StockedGoods stockedGoods =
                    new StockedGoods((Integer) null, "chicken", (byte) 1, new BigDecimal(1.0), new BigDecimal(1.0), new BigDecimal(1.0), (byte) 1, new BigDecimal(1.0), new BigDecimal(1.0), 3, "brand", 1, (byte) 1, "String pic", true, "String descriptio");
            stockedGoodsMapper.insertSelective(stockedGoods);
            SaledGoods saledGoods = new SaledGoods(order.getOrderId(), stockedGoods.getId(), BigDecimal.valueOf(1.0), BigDecimal.valueOf(1.0), 3, "comment");
            saledGoodsMapper.insert(saledGoods);
        }

        @Test
        @Transactional
        @Rollback
        void getUserInfo() throws Exception {
            init();
            int userId = user.getId();
            MvcResult result = mockMvc.perform(get("/user?user_id=" + userId)
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0, obj.get("code"));
            JSONObject user = obj.getJSONObject("data");
            for (String key : user.keySet()) {
                Assertions.assertNotNull(user.get(key));
            }
        }

        @Test
        @Transactional
        @Rollback
        void updateUserPassword() throws Exception {
            init();
            MvcResult result = mockMvc.perform(get("/user/update_password?user_id=" + user.getId()
                    + "&old_pass=wrongpass" + "&new_pass=newpass")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertNotEquals(0, obj.get("code"));

            result = mockMvc.perform(get("/user/update_password?user_id=" + user.getId()
                    + "&old_pass=" + user.getPassword() + "&new_pass=newPass")
                    .content("")).andReturn();
            response = result.getResponse();
            obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0, obj.get("code"));
            User newUser = userMapper.selectByPrimaryKey(user.getId());
            Assertions.assertEquals("newPass", newUser.getPassword());
        }

        @Test
        @Transactional
        @Rollback
        void updateUserAddress() throws Exception {
            init();
            MvcResult result = mockMvc.perform(get("/user/update_address?user_id=" + user.getId()
                    + "&address=newAddress")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0, obj.get("code"));
            User newUser = userMapper.selectByPrimaryKey(user.getId());
            Assertions.assertEquals("newAddress", newUser.getLocation());
        }

        @Test
        @Transactional
        @Rollback
        void searchUserOrders() throws Exception {
            init();
            prepareOrder();
            MvcResult result = mockMvc.perform(get("/user/orders?user_id=" + user.getId() + "&position=0")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0, obj.get("code"));
            JSONArray data = ((JSONArray) obj.get("data"));
            Assertions.assertTrue(data.size() >= 1);
            OrderDetail orderDetail = (OrderDetail) data.getJSONObject(0).toJavaObject(OrderDetail.class);
            Assertions.assertTrue(orderDetail.getGoodsList().size() >= 1);
            Assertions.assertNotNull(orderDetail.getOrderId());
            Assertions.assertNotNull(orderDetail.getGoodsList().get(0).getName());
        }

    }


    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestNestPart2 {

        private String baseUrl = "http://localhost:8080";
        User user = null;

        @Test
        @org.junit.jupiter.api.Order(1)
        void register() throws Exception {
            user = new User();
            user.setEmail(UUID.randomUUID().toString());
            user.setPassword(UUID.randomUUID().toString());
            user.setName(UUID.randomUUID().toString());
            user.setType((byte)1);

            MvcResult result = mockMvc.perform(
                    post(baseUrl + "/register")
                            .content(JSONObject.toJSONString(user))
                            .contentType(MediaType.APPLICATION_JSON)
            ).andReturn();
            String resp = result.getResponse().getContentAsString();
            assertEquals(Constants.SUCCESS_CODE, RespChecker.getCode(resp));
            assertEquals(Constants.OPERATE_SUCCESS, RespChecker.getMsg(resp));
            assertEquals(true, RespChecker.getDataAsPrimitiveType(resp, Boolean.class));


            UserExample example = new UserExample();
            example.createCriteria()
                    .andPasswordEqualTo(user.getPassword())
                    .andEmailEqualTo(user.getEmail())
                    .andNameEqualTo(user.getName());
            List<User> users = userMapper.selectByExample(example);
            assertEquals(1, users.size());
            // set id
            user.setId(users.get(0).getId());
        }

        @Test
        @org.junit.jupiter.api.Order(2)
        void login() throws Exception {
            MvcResult result = mockMvc.perform(
                    post(baseUrl + "/login")
                            .content(JSONObject.toJSONString(user))
                            .contentType(MediaType.APPLICATION_JSON)
            ).andReturn();
            String resp = result.getResponse().getContentAsString();
            assertEquals(Constants.SUCCESS_CODE, RespChecker.getCode(resp));
            assertEquals(Constants.OPERATE_SUCCESS, RespChecker.getMsg(resp));
            LoginedUserInfo info = RespChecker.getDataAsObject(resp, LoginedUserInfo.class);
            assertEquals(user.getId(), info.getId());
            assertEquals(user.getName(), info.getName());
            assertEquals((int)user.getType(), info.getType());
            String token = info.getToken();
            assertNotNull(token);
            UserJwtPayload userJwtPayload = JwtUtils.verifyToken(token);
            assertNotNull(userJwtPayload);
            assertEquals(user.getId(), userJwtPayload.getId());
            assertEquals((int)user.getType(), userJwtPayload.getType());
            assertFalse(TimeUtils.isExpire(userJwtPayload.getExpire()));

        }

        @Test
        @org.junit.jupiter.api.Order(3)
        void logout() throws Exception {
            MvcResult result = mockMvc.perform(
                    put(baseUrl + "/logout")
                            .content(JSONObject.toJSONString(user))
                            .contentType(MediaType.APPLICATION_JSON)
            ).andReturn();
            String resp = result.getResponse().getContentAsString();
            assertEquals(Constants.SUCCESS_CODE, RespChecker.getCode(resp));
            assertEquals(Constants.OPERATE_SUCCESS, RespChecker.getMsg(resp));
            LoginedUserInfo info = RespChecker.getDataAsObject(resp, LoginedUserInfo.class);
            assertEquals(user.getId(), info.getId());
            assertEquals(user.getName(), info.getName());
            assertEquals((int)user.getType(), info.getType());
            String token = info.getToken();
            assertNotNull(token);
            UserJwtPayload userJwtPayload = JwtUtils.verifyToken(token);
            assertNotNull(userJwtPayload);
            assertEquals(user.getId(), userJwtPayload.getId());
            assertEquals((int)user.getType(), userJwtPayload.getType());
            assertTrue(TimeUtils.isExpire(userJwtPayload.getExpire()));
        }

        @AfterAll
        void cleanUp(){
            int num = userMapper.deleteByPrimaryKey(user.getId());
            assertEquals(1, num);
            user = null;
        }

    }


}