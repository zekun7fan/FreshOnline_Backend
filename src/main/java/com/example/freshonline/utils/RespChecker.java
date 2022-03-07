package com.example.freshonline.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.RespConstant;

import java.util.HashSet;
import java.util.List;

public class RespChecker {

    private static HashSet<Class<?>> primitiveClassSet = new HashSet<>(){
        {
            add(Integer.class);
            add(Float.class);
            add(Double.class);
            add(String.class);
            add(Boolean.class);
            add(Byte.class);
            add(Character.class);
        }
    };

    private static boolean isPrimitiveType(Object obj){
        return primitiveClassSet.contains(obj.getClass());
    }

    private static <R> R getPrimitiveTypeData(Object dataObj, Class<R> dtoClass){
        Class<?> dataClazz = dataObj.getClass();
        return dtoClass.equals(dataClazz) ? (R) dataObj : null;
    }


    public static Integer getCode(JSONObject response){
        return response.getInteger(RespConstant.CODE);
    }

    public static Integer getCode(String response){
        JSONObject jsonObject = JSONObject.parseObject(response);
        return getCode(jsonObject);
    }

    public static String getMsg(JSONObject response){
        return response.getString(RespConstant.MSG);
    }

    public static String getMsg(String response){
        JSONObject jsonObject = JSONObject.parseObject(response);
        return getMsg(jsonObject);
    }

    public static <R> R getDataAsPrimitiveType(JSONObject response, Class<R> primitiveType){
        Object dataObj = response.get(RespConstant.DATA);
        return primitiveType.cast(dataObj);
    }

    public static <R> R  getDataAsPrimitiveType(String response, Class<R> primitiveType){
        JSONObject jsonObject = JSONObject.parseObject(response);
        return getDataAsPrimitiveType(jsonObject, primitiveType);
    }

    public static <R> R getDataAsObject(JSONObject response, Class<R> dtoClass){
        JSONObject data = response.getJSONObject(RespConstant.DATA);
        return JSONObject.toJavaObject(data, dtoClass);
    }

    public static <R> R getDataAsObject(String response, Class<R> dtoClass){
        JSONObject jsonObject = JSONObject.parseObject(response);
        return getDataAsObject(jsonObject, dtoClass);
    }

    public static <R> List<R> getDataAsArray(JSONObject response, Class<R> elementClass){
        JSONArray jsonArray = response.getJSONArray(RespConstant.DATA);
        return JSONObject.parseArray(jsonArray.toJSONString(), elementClass);
    }

    public static <R> List<R> getDataAsArray(String response, Class<R> elementClass){
        JSONObject jsonObject = JSONObject.parseObject(response);
        return getDataAsArray(jsonObject, elementClass);
    }




}
