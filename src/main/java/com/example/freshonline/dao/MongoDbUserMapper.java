package com.example.freshonline.dao;

import com.example.freshonline.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoDbUserMapper extends MongoRepository<User, String> {
    @Query("{id:?0}")
    User findItemById(Integer id);

    @Query("{name:'?0'}")
    User findItemByName(String name);

    @Query(value="{type:?0}", fields="{'name' : 1, 'email' : 1}")
    List<User> findAll(int type);

    public long count();
}
