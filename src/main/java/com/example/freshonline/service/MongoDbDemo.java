package com.example.freshonline.service;

import com.example.freshonline.dao.MongoDbUserMapper;
import com.example.freshonline.dao.UserMapper;
import com.example.freshonline.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoDbDemo {
    @Autowired
    MongoDbUserMapper mongodb;

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Integer id){
        User user = mongodb.findItemById(id);
        return user;
    }

    public User getUserByName(String name){
        User user = mongodb.findItemByName(name);
        return user;
    }

    public List<User> getUserByType(int type){
        List<User> users = mongodb.findAll(type);
        return users;
    }

    public void deleteUserMongo(Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        mongodb.delete(user);
    }
    public void copyUserToMongo(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        mongodb.save(user);
    }

}
