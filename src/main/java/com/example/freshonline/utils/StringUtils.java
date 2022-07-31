package com.example.freshonline.utils;

import java.util.*;
import java.util.stream.Collectors;

public class StringUtils {


    public static String addPicUrl(String oldUrlList, String url){
        if (oldUrlList == null){
            return url;
        }
        return oldUrlList + "," + url;
    }

    public static String delPicUrl(String oldUrlList, String url){
        if (oldUrlList == null || "".equals(oldUrlList) || !oldUrlList.contains(",")){
            return "";
        }
        List<String> list = Arrays.stream(oldUrlList.split(",")).collect(Collectors.toList());
        list.removeIf(str -> str.equals(url));
        int sz = list.size();
        StringBuilder newUrlList = new StringBuilder();
        for (int i = 0; i < sz; i++) {
            if (i > 0){
                newUrlList.append(",");
            }
            newUrlList.append(list.get(i));
        }
        return newUrlList.toString();
    }
}
