package com.example.freshonline.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
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

    private static final String userTokenKey = "user_token";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        return checkAuth(request, response, request.getSession());
        request.getSession().setAttribute(Constants.ID, 5);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String user_token = request.getHeader(userTokenKey);
        String token = JwtUtils.updateToken(user_token);
        response.setHeader(userTokenKey, token);
    }

    private boolean checkAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String token = request.getHeader(Constants.TOKEN_KEY);
        if (token == null){
            onAuthFailed(response, Constants.TO_LOGIN_CODE, Constants.PLEASE_LOGIN_OR_REGISTER);
            return false;
        }
        UserJwtPayload userJwtPayload = JwtUtils.verifyToken(token);
        if (userJwtPayload==null){
            onAuthFailed(response, Constants.TO_LOGIN_CODE,Constants.IDENTITY_INFO_INCORRECT);
            return false;
        }
        if (TimeUtils.isExpire(userJwtPayload.getExpire())){
            onAuthFailed(response, Constants.TO_LOGIN_CODE,Constants.USER_INFO_EXPIRED);
            return false;
        }

        session.setAttribute(Constants.ID, userJwtPayload.getId());
        session.setAttribute(Constants.TYPE, userJwtPayload.getType());
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
