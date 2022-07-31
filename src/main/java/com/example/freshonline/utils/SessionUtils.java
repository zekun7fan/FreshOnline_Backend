package com.example.freshonline.utils;


import com.example.freshonline.constants.UserInfoConstant;
import com.example.freshonline.enums.UserType;
import com.example.freshonline.exception.CustomException;
import com.example.freshonline.exception.CustomizeErrorCode;

import javax.servlet.http.HttpSession;



public class SessionUtils {

    public static Integer getUserId(HttpSession session) {
        Integer id = (Integer) session.getAttribute(UserInfoConstant.ID);
        if (id == null){
            throw new CustomException(CustomizeErrorCode.USER_NOT_LOGIN);
        }
        return id;
    }

    public static UserType getUserType(HttpSession session)  {
        Integer type = (Integer) session.getAttribute(UserInfoConstant.TYPE);
        if (type == null){
            throw new CustomException(CustomizeErrorCode.USER_NOT_LOGIN);
        }
        switch (type){
            case 0 : return UserType.CUSTOMER;
            case 1: return UserType.ADMINISTRATOR;
        }
        return UserType.CUSTOMER;
    }



}
