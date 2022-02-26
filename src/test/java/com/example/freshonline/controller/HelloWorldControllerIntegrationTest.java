package com.example.freshonline.controller;

import com.example.freshonline.dao.FavoriteMapper;
import com.example.freshonline.dao.HelloWorldMapper;
import com.example.freshonline.dto.HelloWorldDto;
import com.example.freshonline.service.FavouriteService;
import com.example.freshonline.service.HelloWorldService;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    /**
     * @Value doesn't work with static
     */

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.spring.datasource.driver-class-name}")
    private static String classname;

    @BeforeAll
    public static void beforeAll(){
        System.out.println("here is beforeAll");
//        System.out.println("beforeAll, " + username);
        System.out.println("beforeAll, " + classname);
    }

    @BeforeEach
    public void before(){
        System.out.println("beforeEach " + username);
        System.out.println("beforeEach, " + classname);
    }

    @Test
    public void test(){
        System.out.println("test, " + username);
        System.out.println("test, " + classname);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5})
    public void testWithInts(int testInt){
        System.out.println("testWithInts, int = " + testInt);
    }

    /**
     * @Nested doesn't work with static
     */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class nestedTest1{
        private String testStr = "testStr";

//        @BeforeAll
//        public static void beforeAllTestInNestedTest1(){
//            System.out.println("beforeAllTestInNestedTest1");
//        }

        @BeforeEach
        public void beforeTestInNestedTest1(){
            System.out.println("beforeeTestInNestedTest1, testStr = " + testStr);
        }

        @Test
        public void testInNestedTest1(){
            System.out.println("testInNestedTest1, testStr = " + testStr);
        }
    }
}