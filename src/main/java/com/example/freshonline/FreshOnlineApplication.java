package com.example.freshonline;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
@MapperScan("com.example.freshonline.dao")
public class FreshOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreshOnlineApplication.class, args);
    }

}
