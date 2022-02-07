package com.example.freshonline.utils;


import com.example.freshonline.constants.RouteList;

import java.util.ArrayList;

public class RouteAuthUtils {


    public static boolean auth(String url, Integer userId, Integer type){
        ArrayList<Integer> params = new ArrayList<>();
        int index = url.indexOf("?");
        String route = url;
        if (index != -1){
            route = url.substring(0, index);
        }
        String[] split = route.split("/");
        StringBuilder routePattern = new StringBuilder();
        for (String s : split){
            try {
                Integer i = Integer.parseInt(s);
                params.add(i);
                routePattern.append("/").append("$");
            }catch (NumberFormatException e){
                routePattern.append("/").append(s);
            }
        }
        routePattern.deleteCharAt(routePattern.length()-1);
        String pattern = routePattern.toString();

        switch (pattern){
            case RouteList.GOODS:
            case RouteList.GOODS_DETAIL:
                return true;
            case RouteList.ADMIN_GOODS:
            case RouteList.ADMIN_STATISTICS:
                return type == 1;
            case RouteList.USER_ORDER:
            case RouteList.USER_DETAIL:
                return userId.equals(params.get(0));
            default:
                return false;
        }
    }
}
