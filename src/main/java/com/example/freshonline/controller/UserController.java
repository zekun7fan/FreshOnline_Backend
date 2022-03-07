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
import org.springframework.web.bind.annotation.*;
import com.example.freshonline.dto.LoginedUserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/user")
    public JSONObject getUserInfo(@RequestParam("user_id") Integer userId) {
        return RespBuilder.create(userService.getUserById(userId), VerifyRule.NOT_NULL, "success", "no such user");
    }

    @GetMapping("/user/update_password")
    public JSONObject updateUserPassword(@RequestParam("user_id") Integer userId,
                                         @RequestParam("old_pass") String oldPass,
                                         @RequestParam("new_pass") String newPass) {
        if (userService.checkUser(userId, oldPass) == null)
            throw new CustomException(CustomizeErrorCode.USER_INFO_INCORRECT);
        String newToken = "newToken";
        User user = new User();
        user.setId(userId);
        user.setPassword(newPass);
        userService.updateUserSelective(user);
        return RespBuilder.create(newToken, VerifyRule.NOT_NULL, "success", "Update fail");
    }

    @GetMapping("/user/update_address")
    public JSONObject updateUserAddress(@RequestParam("user_id") Integer userId,
                                        @RequestParam("address") String address) {
        User user = new User();
        user.setId(userId);
        user.setLocation(address);
        userService.updateUserSelective(user);
        return RespBuilder.create(userService.getUserById(userId), VerifyRule.NOT_NULL, "success", "Update fail");
    }

    @GetMapping("/user/orders")
    public JSONObject searchUserOrders(@RequestParam("user_id") Integer userId, @RequestParam("position") Integer position) {
        return RespBuilder.create(orderService.getOrderByUserId(userId, position), VerifyRule.COLLECTION_NOT_EMPTY, "success", "Query error");
    }

    @PostMapping("/login")
    public JSONObject login(@RequestBody User user) {
        LoginedUserInfo loginedUserInfo = userService.login(user);
        return RespBuilder.create(loginedUserInfo, VerifyRule.NOT_NULL);
    }


    @PostMapping("/register")
    public JSONObject register(@RequestBody User user) {
        boolean res = userService.register(user);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @PutMapping("/logout")
    public JSONObject logout(@RequestBody User user) {
        LoginedUserInfo loginedUserInfo = userService.logout(user);
        return RespBuilder.create(loginedUserInfo, VerifyRule.NOT_NULL);
    }
}


