package com.example.freshonline.dao;

import com.example.freshonline.model.Category;
import com.example.freshonline.model.CategoryExample;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import javax.xml.transform.Source;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@MapperScan("com.example.freshonline.dao")
public class CategoryTest {
    
    
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ResourceLoader resourceLoader;
    
    
    @Test
    public void test1(){
        List<Category> categories = categoryMapper.selectByExample(new CategoryExample());
        System.out.println(categories.size());
        System.out.println(categories);

    }

    @Test
    public void test2(){
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:static");
            System.out.println(file.exists());
        } catch (FileNotFoundException e) {
            System.out.println("***");
        }

    }
}


