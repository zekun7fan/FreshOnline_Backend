package com.example.freshonline.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.exception.CustomException;

import com.example.freshonline.utils.RespBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    void handle(CustomException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(e.getCode());
        try {
            PrintWriter writer = response.getWriter();
            String resp = JSONObject.toJSONString(RespBuilder.createFailedRsp(e.getMessage()));
            writer.write(resp);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
