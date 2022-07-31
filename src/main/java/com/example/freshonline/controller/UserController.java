package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.exception.CustomException;
import com.example.freshonline.exception.CustomizeErrorCode;
import com.example.freshonline.exception.ICustomizeErrorCode;
import com.example.freshonline.model.User;
import com.example.freshonline.service.OrderService;
import com.example.freshonline.service.UserService;
import com.example.freshonline.utils.RespBuilder;
import com.example.freshonline.utils.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.freshonline.dto.LoginedUserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/user")
    @PreAuthorize("checkUserId(#userId, #session)")
    public JSONObject getUserInfo(@RequestParam("user_id") Integer userId, HttpSession session) {
        return RespBuilder.create(userService.getUserById(userId), VerifyRule.NOT_NULL, "success", "no such user");
    }

    @PutMapping("/user/update_password")
    @PreAuthorize("checkUserId(#user.id, #session)")
    public JSONObject updateUserPassword(@RequestBody User user, HttpSession session) {
        boolean res = userService.updatePassword(user);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @PutMapping("/user/update_address")
    @PreAuthorize("checkUserId(#user.id, #session)")
    public JSONObject updateUserAddress(@RequestBody User user, HttpSession session) {
        boolean res = userService.updateAddr(user);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @GetMapping("/user/orders")
    @PreAuthorize("checkUserId(#userId, #session)")
    public JSONObject getUserOrders(@RequestParam("user_id") Integer userId, @RequestParam("position") Integer position, HttpSession session) {
        return RespBuilder.create(orderService.getOrderByUserId(userId, position), VerifyRule.COLLECTION_NOT_EMPTY, "success", "Query error");
    }

    @PutMapping("/toLogin")
    public JSONObject login(@RequestBody User user) {
        LoginedUserInfo loginedUserInfo = userService.login(user);
        return RespBuilder.create(loginedUserInfo, VerifyRule.NOT_NULL);
    }


    @PostMapping("/toRegister")
    public JSONObject register(@RequestBody User user) {
        boolean res = userService.register(user);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @PutMapping("/toLogout")
    public JSONObject logout(@RequestBody User user) {
        LoginedUserInfo loginedUserInfo = userService.logout(user);
        return RespBuilder.create(loginedUserInfo, VerifyRule.NOT_NULL);
    }
}


