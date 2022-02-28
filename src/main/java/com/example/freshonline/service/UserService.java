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
import org.springframework.util.ResourceUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public String updateUserSelective(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return "";
    }

    public User checkUser(Integer userId, String passWord) {
        User user = new User();
        user.setId(userId);
        user.setPassword(passWord);
        return userMapper.selectUserByIdPassword(user);
    }

    public LoginedUserInfo login(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(user.getEmail()).andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(userExample);
        if (users == null || users.isEmpty()) {
            return null;
        }
        User u = users.get(0);
        return getLoginedUserInfo(u, true);
    }


    public boolean register(User user) {
        return userMapper.insertSelective(user) == 1;
    }

    public LoginedUserInfo logout(User user) {
        User u = userMapper.selectByPrimaryKey(user.getId());
        if (u == null) {
            return null;
        }
        return getLoginedUserInfo(u, false);
    }

    private LoginedUserInfo getLoginedUserInfo(User u, boolean renew){
        LocalDateTime expire = renew ? TimeUtils.after30days() : TimeUtils.before1day();
        UserJwtPayload userJwtPayload = new UserJwtPayload(u.getId(), Integer.valueOf(u.getType()), expire);
        String token = JwtUtils.createToken(userJwtPayload);
        return token != null ? new LoginedUserInfo(u.getId(), u.getName(), Integer.valueOf(u.getType()), token) : null;
    }



}
