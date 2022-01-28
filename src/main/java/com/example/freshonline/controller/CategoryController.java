package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    /**
     * @author Josh Sun
     * @return JSON
     */
    @GetMapping("/goods/categoryTree")
    public JSONObject getCategoryTree(){
        return new CategoryService().getCategoryTree();
    }

}
