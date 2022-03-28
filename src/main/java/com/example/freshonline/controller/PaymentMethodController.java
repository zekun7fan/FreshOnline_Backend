package com.example.freshonline.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.PaymentMethod;
import com.example.freshonline.service.PaymentMethodService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;


    @PostMapping("/paymentMethod")
    public JSONObject addPaymentMethod(@RequestBody PaymentMethod paymentMethod){
        boolean res = paymentMethodService.addPaymentMethod(paymentMethod);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @DeleteMapping("/paymentMethod/{paymentId}")
    public JSONObject delPaymentMethod(@PathVariable("paymentId") Integer pid){
        boolean res = paymentMethodService.delPaymentMethod(pid);
        return RespBuilder.create(res, VerifyRule.TRUE);
    }

    @GetMapping("/paymentMethod/{userId}")
    public JSONObject getPaymentMethod(@PathVariable("userId") Integer userId){
        List<PaymentMethod> list = paymentMethodService.getPaymentMethodByUserId(userId);
        return RespBuilder.create(list, VerifyRule.NOT_NULL);

    }
}
