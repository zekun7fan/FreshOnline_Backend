package com.example.freshonline.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginedUserInfo {

    private Integer id;

    private String name;

    private Integer type;

    private String token;
}
