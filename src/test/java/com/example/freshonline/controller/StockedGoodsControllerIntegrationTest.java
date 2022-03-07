package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
import com.example.freshonline.dao.CategoryMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.model.Category;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.model.StockedGoodsExample;
import com.example.freshonline.utils.RespChecker;
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
import org.springframework.http.MediaType;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class StockedGoodsControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Nested
    class TestNestPart1 {

        public void init() {
            StockedGoods stockedGoods =
                    new StockedGoods((Integer) null, "chicken", (byte) 1, new BigDecimal(1.0), new BigDecimal(1.0), new BigDecimal(1.0), (byte) 1, new BigDecimal(1.0), new BigDecimal(1.0), 3, "brand", 1, (byte) 1, "String pic", true, "String descriptio");
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
            Assertions.assertEquals(0, obj.get("code"));
            Assertions.assertTrue(((JSONArray) obj.get("data")).size() >= 1);
        }

        @Test
        @Transactional
        @Rollback
        void getRandomGoods() throws Exception {
            init();
            MvcResult result = mockMvc.perform(get("/random_goods?catogory_id_list=1,3")
                    .content("")).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject obj = JSONObject.parseObject(response.getContentAsString());
            Assertions.assertEquals(0, obj.get("code"));
            Assertions.assertTrue(((JSONArray) obj.get("data")).size() >= 1);
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestNestPart2 {
        private String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false";
        private String classname = "com.mysql.cj.jdbc.Driver";
        private String username = "root";
        private String password = "";

        @ParameterizedTest
        @CsvSource(delimiter = ';',
                value = {
                        "/goods?price_low=1&price_high=30&brands=&sort_type=0&keyword=&page=1&category_id=&num_per_page=20;6;-1",
                        "/goods?price_low=5&price_high=100&brands=OPQ&sort_type=0&keyword=&page=1&category_id=&num_per_page=20;0;-1",
                        "/goods?brands=ABC,TNT&price_low=0&price_high=1000&sort_type=0&keyword=&page=1&category_id=&num_per_page=20;5;-1",
                        "/goods?price_low=1&price_high=30&category_id=123&brands=&sort_type=0&keyword=&page=1&num_per_page=20;5;-1",
                        "/goods?category_id=123,124&price_low=0&price_high=1000&brands=&sort_type=0&keyword=&page=1&num_per_page=20;6;-1",
                        "/goods?price_low=5&price_high=100&brands=&page=3&sort_type=0&keyword=&category_id=&num_per_page=20;7;-1",
                })
        public void getSearch(String url, int expectedGoodsTotal, int expectedFirstGoodsId) throws Exception {

            MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();
            System.out.println("response type: " + mvcResult.getResponse().getContentType());
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            JSONObject data = (JSONObject) resp.get("data");
            JSONArray stockedGoodsList = data.getJSONArray("goods_list");
            int goods_total = (int) data.get("goods_total");
            Assert.assertEquals(goods_total, expectedGoodsTotal);
            if (goods_total != 0 && expectedFirstGoodsId != -1) {
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
            System.out.println("set up");
        }

        @AfterAll
        public void finish() throws ClassNotFoundException, SQLException, FileNotFoundException {
            executeSqlScript("stocked_goods_test_finish.sql");
        }
    }

    @Test
    void getGoodsDetails() {
    }



    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestNestPart3{
        private String url = "/goodsdetails/";
        private StockedGoods stockedGoods;
        Category cate1;
        Category cate2;
        Category cate3;


        @BeforeAll
        public void setup() {

            this.cate1 = new Category((Integer) null, 0, "cate1", 1);
            categoryMapper.insertSelective(cate1);
            this.cate2 = new Category((Integer) null, cate1.getId(), "cate2", 1);
            categoryMapper.insertSelective(cate2);
            this.cate3 = new Category((Integer) null, cate2.getId(), "cate3", 1);
            categoryMapper.insertSelective(cate3);
            this.stockedGoods =
                    new StockedGoods((Integer) null, "testproduct", (byte)1, new BigDecimal(9.99), new BigDecimal(1000), new BigDecimal(100), (byte) 0, (BigDecimal) null, new BigDecimal(3.5),10, "brandname1", cate3.getId(), (byte)1, "String pic", true, "String descriptio");
            stockedGoodsMapper.insertSelective(stockedGoods);
        }


        @Test
        public void getGoodsDetailsSuccess() throws Exception{
            // assert success
            MvcResult mvcResult = mockMvc.perform(get(url+stockedGoods.getId().toString())).andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            JSONObject data = (JSONObject) resp.get("data");
            Assertions.assertEquals(0,resp.get("code"));
            Assertions.assertEquals(stockedGoods.getId(), data.get("id"));
            Assertions.assertEquals(cate3.getId(), ((JSONObject) data.get("cate3")).get("id"));
        }

        @Test
        public void getGoodsDetailsMissingID() throws Exception{
            // assert success
            MvcResult mvcResult = mockMvc.perform(get(url+"0")).andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            JSONObject data = (JSONObject) resp.get("data");
            Assertions.assertEquals(0,resp.get("code"));
            Assertions.assertNull(data);
        }

        @Test
        public void getGoodsDetailsBadInputs() throws Exception{
            // assert success
            MvcResult mvcResult = mockMvc.perform(get(url+"badinputshshshsh")).andReturn();
            JSONObject resp = JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertEquals(1,resp.get("code"));
            Assertions.assertNotNull(resp.get("msg"));
        }




        @AfterAll
        public void finish(){
            stockedGoodsMapper.deleteByPrimaryKey(stockedGoods.getId());
            categoryMapper.deleteByPrimaryKey(cate3.getId());
            categoryMapper.deleteByPrimaryKey(cate2.getId());
            categoryMapper.deleteByPrimaryKey(cate1.getId());
        }
    }



    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestNestPart4 {

        private final String baseUrl = "http://localhost:8080";

        private StockedGoods goods1 = null;
        private final String name1 = UUID.randomUUID().toString();
        private final boolean active1 = true;
        private final BigDecimal price1 = new BigDecimal("223.0");
        private final byte onsale1 = 1;
        private final BigDecimal salePrice1 = new BigDecimal("113.0");
        private final String brand1 = "temp brand";
        private final Integer categoryId1 = 40;
        private final String description1 = "temp description";
        private final byte isNew1 = 1;
        private final byte type1 = 1;
        private final BigDecimal storage1 = new BigDecimal("550.0");
        private final BigDecimal rate1 = new BigDecimal("3.0");
        private final Integer rateCount1 = 5;
        private final BigDecimal sales1 = new BigDecimal("88.0");
        private final String pic1 = "";

        private final String nameForUpdate = UUID.randomUUID().toString();
        private final Integer categoryIdforUpdate = 22;
        private final BigDecimal priceforUpdate = new BigDecimal("73.0");


        @BeforeAll
        void setUp() {
            goods1 = new StockedGoods(null, name1, type1, price1, storage1, sales1, onsale1, salePrice1, rate1, rateCount1, brand1, categoryId1, isNew1, pic1, active1, description1);
        }


        @Test
        void uploadGoodsPictures() {
        }

        @Test
        void deleteGoodsPictures() {
        }

        @Test
        @Order(1)
        void addGoods() throws Exception {
            // arrange
            assertNotNull(goods1);
            //act
            MvcResult result = mockMvc.perform(post(baseUrl + "/goods")
                    .content(JSONObject.toJSONString(goods1))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andReturn();

            //assert
            String resp = result.getResponse().getContentAsString();
            Integer code = RespChecker.getCode(resp);
            assertEquals(Constants.SUCCESS_CODE, code);
            String msg = RespChecker.getMsg(resp);
            assertEquals(Constants.OPERATE_SUCCESS, msg);
            Boolean data = RespChecker.getDataAsPrimitiveType(resp, Boolean.class);
            assertTrue(data);

            StockedGoodsExample example = new StockedGoodsExample();
            example.createCriteria().andNameEqualTo(name1);
            List<StockedGoods> stockedGoods = stockedGoodsMapper.selectByExample(example);
            assertEquals(1, stockedGoods.size());
            StockedGoods stockedGoods1 = stockedGoods.get(0);
            // set id, for upcoming update
            goods1.setId(stockedGoods1.getId());
//            assertEquals(goods1, stockedGoods1);
        }

        @Test
        @Order(2)
        void updateGoods() throws Exception {
            // arrange
            goods1.setPrice(priceforUpdate);
            goods1.setCategoryId(categoryIdforUpdate);
            goods1.setName(nameForUpdate);

            //act
            MvcResult result = mockMvc.perform(put(baseUrl + "/goods")
                    .content(JSONObject.toJSONString(goods1))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andReturn();

            //assert
            String resp = result.getResponse().getContentAsString();
            Integer code = RespChecker.getCode(resp);
            assertEquals(Constants.SUCCESS_CODE, code);
            String msg = RespChecker.getMsg(resp);
            assertEquals(Constants.OPERATE_SUCCESS, msg);
            Boolean data = RespChecker.getDataAsPrimitiveType(resp, Boolean.class);
            assertTrue(data);

            StockedGoodsExample example = new StockedGoodsExample();
            example.createCriteria()
                    .andPriceEqualTo(priceforUpdate)
                    .andCategoryIdEqualTo(categoryIdforUpdate)
                    .andNameEqualTo(nameForUpdate);

            List<StockedGoods> stockedGoods = stockedGoodsMapper.selectByExample(example);
            assertEquals(1, stockedGoods.size());
//            StockedGoods stockedGoods1 = stockedGoods.get(0);
//            assertEquals(goods1, stockedGoods1);
        }

        @Test
        @Order(3)
        void deleteGoods() throws Exception {

            // act
            MvcResult result = mockMvc.perform(delete(baseUrl + "/goods/" + goods1.getId())).andReturn();
            //assert
            String resp = result.getResponse().getContentAsString();
            Integer code = RespChecker.getCode(resp);
            assertEquals(Constants.SUCCESS_CODE, code);
            String msg = RespChecker.getMsg(resp);
            assertEquals(Constants.OPERATE_SUCCESS, msg);
            Boolean data = RespChecker.getDataAsPrimitiveType(resp, Boolean.class);
            assertTrue(data);
            StockedGoodsExample example = new StockedGoodsExample();
            example.createCriteria().andIdEqualTo(goods1.getId());
            List<StockedGoods> stockedGoods = stockedGoodsMapper.selectByExample(example);
            assertEquals(0, stockedGoods.size());

        }

        @AfterAll
        void cleanUp() {
            goods1 = null;
        }
    }

}