package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.model.StockedGoods;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class StockedGoodsControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    @Nested
    class TestNestPart1{

        public void init() {
            StockedGoods stockedGoods =
                        new StockedGoods((Integer) null, "chicken", (byte)1, new BigDecimal(1.0), new BigDecimal(1.0), new BigDecimal(1.0), (byte) 1, new BigDecimal(1.0),new BigDecimal(1.0), 3, "brand", 1, (byte)1, "String pic", true, "String descriptio");
            stockedGoodsMapper.insertSelective(stockedGoods);
        }

        @Test
        @Transactional
        @Rollback
        void getWeeklySpecial() throws Exception {
            init();
            MvcResult result = mockMvc.perform(get("/weekly_special")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0,obj.get("code"));
            Assertions.assertTrue(((JSONArray)obj.get("data")).size()>=1);
        }
        @Test
        @Transactional
        @Rollback
        void getRandomGoods() throws Exception {
            init();
            MvcResult result = mockMvc.perform(get("/random_goods?catogory_id_list=[1]")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0,obj.get("code"));
            Assertions.assertTrue(((JSONArray)obj.get("data")).size()>=1);
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestNestPart2{
        private String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false";
        private String classname = "com.mysql.cj.jdbc.Driver";
        private String username = "root";
        private String password = "";

        @ParameterizedTest
        @CsvSource({
                "http://localhost:8080/goods,7,1",
                "http://localhost:8080/goods,7,1"})
        public void getSearch(String url, int expectedGoodsTotal, int expectedFirstGoodsId) throws Exception{

            MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();
            System.out.println("response type: " + mvcResult.getResponse().getContentType());
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            JSONObject data = (JSONObject) resp.get("data");
            JSONArray stockedGoodsList = data.getJSONArray("goods_list");
            int goods_total = (int) data.get("goods_total");
            Assert.assertEquals(goods_total, expectedGoodsTotal);
            if (goods_total != 0){
                int firstGoodsId = stockedGoodsList.getJSONObject(0).getInteger("id");
                Assert.assertEquals(firstGoodsId, expectedFirstGoodsId);
            }
        }

        private void executeSqlScript(String scriptName) throws ClassNotFoundException, SQLException, FileNotFoundException {
            Class.forName(classname);
            Connection connection = DriverManager.getConnection(url, username, password);
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            Resources.setCharset(Charset.forName("UTF8"));
            scriptRunner.runScript(new FileReader(
                    System.getProperty("user.dir") + "/src/test/static.test-sqls/" + scriptName));
            connection.close();
        }

        @BeforeAll
        public void setup() throws ClassNotFoundException, SQLException, FileNotFoundException {
            executeSqlScript("stocked_goods_test_init.sql");
        }

        @AfterAll
        public void finish() throws ClassNotFoundException, SQLException, FileNotFoundException {
            executeSqlScript("stocked_goods_test_finish.sql");
        }
    }

    @Test
    void getGoodsDetails() {
    }


    @Test
    void uploadGoodsPictures() {
    }

    @Test
    void deleteGoodsPictures() {
    }

    @Test
    void addGoods() {
    }

    @Test
    void updateGoods() {
    }

    @Test
    void deleteGoods() {
    }

}