package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.RespConstant;
import com.example.freshonline.dto.CategoryTreeNode;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.Category;
import com.example.freshonline.service.CategoryService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryTree")
    public JSONObject getCategoryTree() {
        List<CategoryTreeNode> categoryTree = categoryService.getCategoryTree();
        return RespBuilder.create(categoryTree, VerifyRule.NOT_NULL, RespConstant.OPERATE_SUCCESS, RespConstant.OPERATE_FAIL);
    }

    @PostMapping("/category")
    @PreAuthorize("isAdmin(#session)")
    public JSONObject addCategory(@RequestBody Category category, HttpSession session) {
        boolean res = categoryService.addCategory(category);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @DeleteMapping("/category")
    @PreAuthorize("isAdmin(#session)")
    public JSONObject delCategory(@Valid @RequestBody CategoryTreeNode node, HttpSession session) {
        List<CategoryTreeNode> failedDelNodeList = categoryService.delSubCategoryTree(node);
        return RespBuilder.create(failedDelNodeList, VerifyRule.COLLECTION_EMPTY);
    }


}
