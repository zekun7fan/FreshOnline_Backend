package com.example.freshonline.dto;

import java.util.Map;

public interface VerifyRequestData <T>{


    default T verifyParams(Map<String, String> requestParamMap){
        return null;
    };

    default T verifyBody(Map<String, String> requestBodyMap){
        return null;
    };

    default T verifyParamsAndBody(Map<String, String> requestParamMap, Map<String, String> requestBodyMap){
        return null;
    }

}
