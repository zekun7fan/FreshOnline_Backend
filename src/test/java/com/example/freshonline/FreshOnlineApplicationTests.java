package com.example.freshonline;

import com.example.freshonline.dao.CategoryMapper;
import com.example.freshonline.model.Category;
import com.example.freshonline.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.example.freshonline.dao")
class FreshOnlineApplicationTests {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void contextLoads() {
    }



    @Test
    public void test1() {
        Category category = categoryMapper.selectByPrimaryKey(1);
    }




}
