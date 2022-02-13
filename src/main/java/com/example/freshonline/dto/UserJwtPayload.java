package com.example.freshonline.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJwtPayload {
    Integer id;
    Integer type;
    LocalDateTime expire;
}
