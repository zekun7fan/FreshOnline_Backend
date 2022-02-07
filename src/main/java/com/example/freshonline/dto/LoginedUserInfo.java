package com.example.freshonline.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginedUserInfo implements VerifyRequestData<LoginedUserInfo>{

    private Integer id;

    private String name;

    private Integer type;

    private String token;

    @Override
    public LoginedUserInfo verifyBody(Map<String, String> requestBodyMap) {
        return VerifyRequestData.super.verifyBody(requestBodyMap);
    }
}
