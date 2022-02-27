package com.example.freshonline.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.exception.CustomExceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomExceptions.class)
    void handle(CustomExceptions e, HttpServletRequest request, HttpServletResponse response) {
        try {
             JSONObject jsonbody = new JSONObject();
             jsonbody.put("msg","asddasdsaasd");
            response.setContentType("application/json");
            response.setStatus(700);
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonbody));
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
