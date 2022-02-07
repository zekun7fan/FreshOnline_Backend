package com.example.freshonline.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
import com.example.freshonline.dto.UserJwtPayload;
import com.example.freshonline.utils.JwtUtils;
import com.example.freshonline.utils.RespBuilder;
import com.example.freshonline.utils.RouteAuthUtils;
import com.example.freshonline.utils.TimeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;



@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("jjjjj");
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String user_token = request.getHeader("user_token");
        String token = JwtUtils.updateToken(user_token);
        response.setHeader("user_token", token);
    }

    private boolean isTokenValid(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader(Constants.TOKEN_KEY);
        if (token == null){
            authFailed(response, Constants.TO_LOGIN_CODE, Constants.PLEASE_LOGIN_OR_REGISTER);
            return false;
        }
        UserJwtPayload userJwtPayload = JwtUtils.verifyToken(token);
        if (userJwtPayload==null){
            authFailed(response, Constants.TO_LOGIN_CODE,Constants.IDENTITY_INFO_INCORRECT);
            return false;
        }
        if (TimeUtils.isExpire(userJwtPayload.getExpire())){
            authFailed(response, Constants.TO_LOGIN_CODE,Constants.USER_INFO_EXPIRED);
            return false;
        }

        Integer id = userJwtPayload.getId();
        Integer type = userJwtPayload.getType();
        String route = request.getRequestURI();
        if (!RouteAuthUtils.auth(route, id, type)){
            authFailed(response, Constants.TO_HOMEPAGE_CODE, Constants.PERMISSION_DENIED);
            return false;
        }
        return true;
    }

    private void authFailed(HttpServletResponse response, Integer errorCode, String failedMsg){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        JSONObject resp = RespBuilder.createFailedRsp(errorCode, failedMsg);
        try {
            response.getWriter().write(resp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
