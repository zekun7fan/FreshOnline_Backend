package com.example.freshonline.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dto.LoginedUserInfo;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.User;
import com.example.freshonline.service.UserService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject jsonObject){
        User user = jsonObject.toJavaObject(User.class);
        LoginedUserInfo loginedUserInfo = userService.login(user);
        return RespBuilder.create(loginedUserInfo, VerifyRule.NOT_NULL);
    }


    @PostMapping("/register")
    public JSONObject register(@RequestBody JSONObject jsonObject){
        User user = jsonObject.toJavaObject(User.class);
        boolean res = userService.register(user);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

}
