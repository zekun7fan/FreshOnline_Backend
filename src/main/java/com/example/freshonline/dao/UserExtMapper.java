package com.example.freshonline.dao;

import com.example.freshonline.model.User;


public interface UserExtMapper {


    User selectUserByIdPassword(User u);
}
