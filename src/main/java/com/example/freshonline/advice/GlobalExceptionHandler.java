package com.example.freshonline.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.exception.CustomException;

import com.example.freshonline.exception.CustomizeErrorCode;
import com.example.freshonline.utils.RespBuilder;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

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

    @ExceptionHandler(NumberFormatException.class)
    void handle(NumberFormatException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(Response.SC_OK);
        try {
            PrintWriter writer = response.getWriter();
            String resp = JSONObject.toJSONString(RespBuilder.createFailedRsp("Wrong parameter format!"));
            writer.write(resp);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
<<<<<<< HEAD

    // @ExceptionHandler(ConnectException.class)
    // void handle(ConnectException e, HttpServletRequest request, HttpServletResponse response) {
    //     response.setStatus(Response.SC_OK);
    //     try {
    //         PrintWriter writer = response.getWriter();
    //         String resp = JSONObject.toJSONString(RespBuilder.createFailedRsp("Network Exception!"));
    //         writer.write(resp);
    //     } catch (IOException ex) {
    //         ex.printStackTrace();
    //     }
    // }

=======
>>>>>>> 407df55148080df832ea9720dbea1dd0ae084544
    
}
