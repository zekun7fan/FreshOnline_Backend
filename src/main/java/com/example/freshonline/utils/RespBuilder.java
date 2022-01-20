package com.example.freshonline.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.constants.Constants;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;

/**
 * response builder
 * @author zekun
 */
public class RespBuilder {

    public static JSONObject create(Object data, VerifyRule rule, String success_msg, String fail_msg) {
        JSONObject resp = new JSONObject();
        if (rule.verify(data)){
            resp.put(Constants.CODE, Constants.SUCCESS_CODE);
            resp.put(Constants.MSG, success_msg);
            resp.put(Constants.DATA, data);
        }else{
            resp.put(Constants.CODE, Constants.FAIL_CODE);
            resp.put(Constants.MSG, fail_msg);
        }
        return resp;
    }
}
