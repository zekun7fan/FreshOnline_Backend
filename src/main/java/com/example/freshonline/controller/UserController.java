package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.User;
import com.example.freshonline.service.OrderService;
import com.example.freshonline.service.UserService;
import com.example.freshonline.utils.RespBuilder;
import com.example.freshonline.utils.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.freshonline.dto.LoginedUserInfo;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/user")
    public JSONObject getUserInfo(@RequestParam("user_id") Integer userId){
        return RespBuilder.create(userService.getUserById(userId),VerifyRule.NOT_NULL,"success","no such user");
    }

    @GetMapping("/user/update_password")
    public JSONObject updateUserPassword(@RequestParam("user_id") String userId,
                                         @RequestParam("old_pass") String oldPass,
                                         @RequestParam("new_pass") String newPass){
        Integer userIdInt = (new ValidationChecker()).str2int(userId,1);
        if(userService.checkUser(userIdInt,oldPass)==null)
            return RespBuilder.create(null, VerifyRule.NOT_NULL,"success","Wrong old password");
        else{
            String newToken="newToken";
            User user = new User();
            user.setId(userIdInt);
            user.setPassword(newPass);
            userService.updateUserSelective(user);
            return RespBuilder.create(newToken, VerifyRule.NOT_NULL,"success","Update fail");
        }
    }
    @GetMapping("/user/update_address")
    public JSONObject updateUserAddress(@RequestParam("user_id") String userId,
                                         @RequestParam("address") String address){
        Integer userIdInt = (new ValidationChecker()).str2int(userId,1);
        User user = new User();
        user.setId(userIdInt);
        user.setLocation(address);
        userService.updateUserSelective(user);
        return RespBuilder.create(userService.getUserById(userIdInt), VerifyRule.NOT_NULL,"success","Update fail");
    }

    @GetMapping("/user/orders")
    public JSONObject searchUserOrders(@RequestParam("user_id") String userId,@RequestParam("position") String position){
        Integer userIdInt = (new ValidationChecker()).str2int(userId,1);
        Integer positionInt = (new ValidationChecker()).str2int(position,1);
        return RespBuilder.create(orderService.getOrderByUserId(userIdInt,positionInt),VerifyRule.COLLECTION_NOT_EMPTY,"success","Query error");
    }

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

    @PutMapping("/logout")
    public JSONObject logout(@RequestBody JSONObject jsonObject){
        User user = jsonObject.toJavaObject(User.class);
        LoginedUserInfo loginedUserInfo = userService.logout(user);
        return RespBuilder.create(loginedUserInfo, VerifyRule.NOT_NULL);
    }



}
