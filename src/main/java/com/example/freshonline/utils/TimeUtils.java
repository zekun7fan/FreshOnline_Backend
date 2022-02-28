package com.example.freshonline.utils;


import java.time.LocalDateTime;

public class TimeUtils {

    public static LocalDateTime after30days(){
        return LocalDateTime.now().plusDays(30);
    }

    public static LocalDateTime before1day(){
        return LocalDateTime.now().minusDays(1);
    }


    public static LocalDateTime after10sec(){
        return LocalDateTime.now().plusSeconds(10);
    }

    public static boolean isExpire(LocalDateTime expire){
        return expire.isBefore(LocalDateTime.now());
    }

}
