package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
import com.example.freshonline.dto.CategoryTreeNode;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.Category;
import com.example.freshonline.service.CategoryService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/goods/categoryTree")
    public JSONObject getCategoryTree(){
        // validate
        // dispatch
        // sum up
        System.out.println(17);
        System.out.println(15);
        System.out.println(15);
        List<CategoryTreeNode> categoryTree = categoryService.getCategoryTree();
        return RespBuilder.create(categoryTree, VerifyRule.NOT_NULL, Constants.OPERATE_SUCCESS, Constants.OPERATE_FAIL);
    }

    @PostMapping("/category")
    public JSONObject addCategory(@RequestBody JSONObject jsonObject){
        System.out.println("7-temp-issue");
        System.out.println("7-temp-issue");
        System.out.println("7-temp-issue");
        System.out.println("7-temp-issue");
        System.out.println("7-temp-issue");
        Category category = jsonObject.toJavaObject(Category.class);
        boolean res = categoryService.addCategory(category);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @DeleteMapping("/category")
    public JSONObject delCategory(@RequestBody JSONObject jsonObject){
        System.out.println("sssss");
        CategoryTreeNode node = jsonObject.toJavaObject(CategoryTreeNode.class);
        List<CategoryTreeNode> failedDelNodeList = categoryService.delSubCategoryTree(node);
        return RespBuilder.create(failedDelNodeList, VerifyRule.COLLECTION_EMPTY);
    }






}
