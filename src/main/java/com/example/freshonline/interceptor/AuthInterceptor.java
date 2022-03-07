package com.example.freshonline.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.RespConstant;
import com.example.freshonline.constants.UserInfoConstant;
import com.example.freshonline.dto.UserJwtPayload;
import com.example.freshonline.utils.JwtUtils;
import com.example.freshonline.utils.RespBuilder;
import com.example.freshonline.utils.TimeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthInterceptor implements HandlerInterceptor {

    private static final String IDENTITY_INFO_INCORRECT = "identity info is incorrect";
    private static final String PLEASE_LOGIN_OR_REGISTER = "please login or register";
    private static final String USER_INFO_EXPIRED = "user info is expired, please login or register again";
    private static final String PERMISSION_DENIED = "you have no permission to view these content";
    private static final String TOKEN_KEY = "fresh_online_token";

    private static final String userTokenKey = "user_token";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        return checkAuth(request, response, request.getSession());
        request.getSession().setAttribute(UserInfoConstant.ID, 5);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String user_token = request.getHeader(userTokenKey);
        String token = JwtUtils.updateToken(user_token);
        response.setHeader(userTokenKey, token);
    }

    private boolean checkAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String token = request.getHeader(TOKEN_KEY);
        if (token == null){
            onAuthFailed(response, RespConstant.TO_LOGIN_CODE, PLEASE_LOGIN_OR_REGISTER);
            return false;
        }
        UserJwtPayload userJwtPayload = JwtUtils.verifyToken(token);
        if (userJwtPayload==null){
            onAuthFailed(response, RespConstant.TO_LOGIN_CODE,IDENTITY_INFO_INCORRECT);
            return false;
        }
        if (TimeUtils.isExpire(userJwtPayload.getExpire())){
            onAuthFailed(response, RespConstant.TO_LOGIN_CODE,USER_INFO_EXPIRED);
            return false;
        }

        session.setAttribute(UserInfoConstant.ID, userJwtPayload.getId());
        session.setAttribute(UserInfoConstant.TYPE, userJwtPayload.getType());
        return true;
    }

    private void onAuthFailed(HttpServletResponse response, Integer errorCode, String failedMsg){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        JSONObject resp = RespBuilder.createFailedRsp(errorCode, failedMsg);
        try {
            response.getWriter().write(resp.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
