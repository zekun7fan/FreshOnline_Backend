package com.example.freshonline.dao;

import com.example.freshonline.model.Category;
import com.example.freshonline.model.CategoryExample;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootTest
@MapperScan("com.example.freshonline.dao")
public class CategoryTest {
    
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    
    @Test
    public void test1(){
        List<Category> categories = categoryMapper.selectByExample(new CategoryExample());
        System.out.println(categories.size());
        System.out.println(categories);

    }

    @Test
    public void test2(){
        Category category = new Category();
        category.setParentId(0);
        category.setName("aaa");
        category.setLevel(1);
        categoryMapper.insert(category);
    }
}
