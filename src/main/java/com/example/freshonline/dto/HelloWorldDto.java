package com.example.freshonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldDto {

    private String name;
    private Integer level;
    private String add;
}
