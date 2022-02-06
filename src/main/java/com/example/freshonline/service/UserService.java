package com.example.freshonline.service;


import com.example.freshonline.dao.UserMapper;
import com.example.freshonline.dto.LoginedUserInfo;
import com.example.freshonline.dto.UserJwtPayload;
import com.example.freshonline.model.User;
import com.example.freshonline.model.UserExample;
import com.example.freshonline.utils.JwtUtils;
import com.example.freshonline.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public LoginedUserInfo login(User user){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(user.getEmail()).andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(userExample);
        if (users== null || users.isEmpty()){
            return null;
        }
        User u = users.get(0);
        UserJwtPayload userJwtPayload = new UserJwtPayload(u.getId(), Integer.valueOf(u.getType()), TimeUtils.after30days());
        String token = JwtUtils.createToken(userJwtPayload);
        return token != null ? new LoginedUserInfo(u.getId(), u.getName(), Integer.valueOf(u.getType()), token) : null;
    }


    public boolean register(User user){
        return userMapper.insertSelective(user) == 1;
    }

}
