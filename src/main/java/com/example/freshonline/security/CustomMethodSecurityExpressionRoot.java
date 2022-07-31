package com.example.freshonline.security;


import com.example.freshonline.enums.UserType;
import com.example.freshonline.utils.SessionUtils;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpSession;
import java.nio.file.FileAlreadyExistsException;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean checkUserId(Integer id, HttpSession session){
        if (id == null) {
            return false;
        }
        return id.equals(SessionUtils.getUserId(session));
    }

    public boolean isAdmin(HttpSession session) {
        return UserType.ADMINISTRATOR.equals(SessionUtils.getUserType(session));
    }

    public boolean isCustomer(HttpSession session) {
        return UserType.CUSTOMER.equals(SessionUtils.getUserType(session));
    }



    @Override
    public void setFilterObject(Object filterObject) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}
