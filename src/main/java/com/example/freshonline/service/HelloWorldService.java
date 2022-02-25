package com.example.freshonline.service;


import com.example.freshonline.dao.HelloWorldMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    @Autowired
    private HelloWorldMapper helloWorldMapper;

    public String m1() throws Exception {
        return helloWorldMapper.m1() + "456";
    }
}
