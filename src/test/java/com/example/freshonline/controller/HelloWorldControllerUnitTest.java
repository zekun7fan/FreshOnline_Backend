package com.example.freshonline.controller;

import com.example.freshonline.service.HelloWorldService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelloWorldService helloWorldService;

    @Test
    public void a1() throws Exception {
        Mockito.when(helloWorldService.a1("123")).thenReturn("test: hello mock");
        MvcResult mvcResult = mockMvc.perform(get("/helloUnitTest?hello_name=123")).andReturn();
        System.out.println("response type: " + mvcResult.getResponse().getContentType());
        System.out.println("response: " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals(mvcResult.getResponse().getContentAsString(), "test: hello mock");
    }
}
