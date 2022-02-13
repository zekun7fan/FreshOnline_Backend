package com.example.freshonline.service;

import com.example.freshonline.dao.UserMapper;
import com.example.freshonline.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public String updateUserSelective(User user){
        userMapper.updateByPrimaryKeySelective(user);
        return "";
    }

    public User checkUser(Integer userId,String passWord){
        User user = new User();
        user.setId(userId);
        user.setPassword(passWord);
        return userMapper.selectUserByIdPassword(user);
    }
}
