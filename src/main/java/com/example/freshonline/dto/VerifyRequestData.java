package com.example.freshonline.dto;

import java.util.Map;

public interface VerifyRequestData {


    default void verifyParams(Map<String, String> requestParamMap) {

    }

    default void verifyBody(Map<String, String> requestBodyMap) {

    }

    default void verifyParamsAndBody(Map<String, String> requestParamMap, Map<String, String> requestBodyMap) {

    }

}
