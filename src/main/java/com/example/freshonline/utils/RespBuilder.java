package com.example.freshonline.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.RespConstant;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;

/**
 * response builder
 * @author zekun
 */
public class RespBuilder {

    public static JSONObject create(Object data, VerifyRule rule, String success_msg, String fail_msg) {
        JSONObject resp = new JSONObject();
        if (rule.verify(data)){
            resp.put(RespConstant.CODE, RespConstant.SUCCESS_CODE);
            resp.put(RespConstant.MSG, success_msg);
            resp.put(RespConstant.DATA, data);
        }else{
            resp.put(RespConstant.CODE, RespConstant.FAIL_CODE);
            resp.put(RespConstant.MSG, fail_msg);
        }
        return resp;
    }


    public static JSONObject create(Object data, VerifyRule rule){
        return create(data, rule, RespConstant.OPERATE_SUCCESS, RespConstant.OPERATE_FAIL);
    }

    public static JSONObject createFailedRsp(String fail_msg){
        JSONObject resp = new JSONObject();
        resp.put(RespConstant.CODE, RespConstant.FAIL_CODE);
        resp.put(RespConstant.MSG, fail_msg);
        return resp;
    }


    public static JSONObject createFailedRsp(Integer errorCode, String fail_msg){
        JSONObject resp = new JSONObject();
        resp.put(RespConstant.CODE, errorCode);
        resp.put(RespConstant.MSG, fail_msg);
        return resp;
    }




}
