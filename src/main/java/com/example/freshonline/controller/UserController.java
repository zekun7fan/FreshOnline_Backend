package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.User;
import com.example.freshonline.service.OrderService;
import com.example.freshonline.service.UserService;
import com.example.freshonline.utils.RespBuilder;
import com.example.freshonline.utils.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public JSONObject updateUserAddress(@RequestParam("user_id") Integer userId,
                                         @RequestParam("address") String address){
        User user = new User();
        user.setId(userId);
        user.setLocation(address);
        userService.updateUserSelective(user);
        return RespBuilder.create(userService.getUserById(userId), VerifyRule.NOT_NULL,"success","Update fail");
    }

    @GetMapping("/user/{user_id}/orders")
    public JSONObject serachUserOrders(@PathVariable("user_id") Integer id){
        return RespBuilder.create(orderService.getOrderByUserId(id),VerifyRule.COLLECTION_NOT_EMPTY,"success","Query error");
    }
}