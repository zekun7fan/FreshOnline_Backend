package com.example.freshonline.dao;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import com.example.freshonline.model.joined_tables.GoodsCategory;
import java.util.List;

@SpringBootTest
@MapperScan("com.example.freshonline.dao")
public class GoodsCategoryTest {
    
    
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    
    
    @Test
    public void test1(){
        GoodsCategory a = goodsCategoryMapper.selectByGoodsID(1);
        System.err.println(a);
    }
}
