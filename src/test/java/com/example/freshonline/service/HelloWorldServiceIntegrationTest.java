package com.example.freshonline.service;

import com.example.freshonline.dao.HelloWorldMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HelloWorldServiceIntegrationTest {

    @Mock
    HelloWorldMapper mapper;

    @InjectMocks
    HelloWorldService service;

//    @Test
//    void m1() {
//        when(mapper.m1()).thenReturn("123");
//        String res = service.m1();
//        assertEquals(res, "123456");
//    }
}