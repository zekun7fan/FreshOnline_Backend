package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
import com.example.freshonline.dto.CategoryTreeNode;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.service.CategoryService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/goods/categoryTree")
    public JSONObject getCategoryTree(){
        List<CategoryTreeNode> categoryTree = categoryService.getCategoryTree();
        return RespBuilder.create(categoryTree, VerifyRule.NOT_NULL, Constants.OPERATE_SUCCESS, Constants.OPERATE_FAIL);
    }

}
