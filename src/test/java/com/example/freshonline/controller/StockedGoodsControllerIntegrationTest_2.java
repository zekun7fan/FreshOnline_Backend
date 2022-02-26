package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class StockedGoodsControllerIntegrationTest_2 {

    @Autowired
    private MockMvc mockMvc;

    private static String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false";
    private static String classname = "com.mysql.cj.jdbc.Driver";
    private static String username = "root";
    private static String password = "";

//    @SuppressWarnings("static-access")
//    @Value("${spring.datasource.url}")
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    @SuppressWarnings("static-access")
//    @Value("${spring.datasource.driver-class-name}")
//    public void setClassname(String classname){
//        this.classname = classname;
//    }
//
//    @SuppressWarnings("static-access")
//    @Value("${spring.datasource.username}")
//    public void setUsername(String username){
//        this.username = username;
//    }
//
//    @SuppressWarnings("static-access")
//    @Value("${spring.datasource.password}")
//    public void setPassword(String password){
//        this.password = password;
//    }

//    md 干 啥也用不了 破大防

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

    /**
     * Exception Test can't work with mock
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "http://localhost:8080/goods?sort_type=1.2",
            "http://localhost:8080/goods?price_low=abcshui"})
    public void getSearchThrowsNumberFormatException(String url) throws Exception {
        Throwable exception = Assert.assertThrows(NumberFormatException.class,
                ()-> { mockMvc.perform(get(url)); });
        System.out.println(exception.getMessage());
    }

    private static void executeSqlScript(String scriptName) throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName(classname);
        Connection connection = DriverManager.getConnection(url, username, password);
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        Resources.setCharset(Charset.forName("UTF8"));
        scriptRunner.runScript(new FileReader(
                System.getProperty("user.dir") + "/src/test/static.test-sqls/" + scriptName));
        connection.close();
    }

    @BeforeAll
    public static void setup() throws ClassNotFoundException, SQLException, FileNotFoundException {
        executeSqlScript("stocked_goods_test_init.sql");
    }

    @AfterAll
    public static void finish() throws ClassNotFoundException, SQLException, FileNotFoundException {
        executeSqlScript("stocked_goods_test_finish.sql");
    }

}