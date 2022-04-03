package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.enums.OrderStatus;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.Order;
import com.example.freshonline.model.User;
import com.example.freshonline.service.OrderService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/payOrder")
    public
    JSONObject payOrder(@RequestBody Order order) {
        order.setPayTime(LocalDateTime.now());
        Order result = orderService.updateOrder(order, (byte)OrderStatus.PAID.ordinal());
        return RespBuilder.create(result.getStatus()==OrderStatus.PAID.ordinal(), VerifyRule.TRUE,
                "Order paid successfully","Payment failed");
    }
    @PostMapping("/cancelOrder")
    public
    JSONObject cancelOrder(@RequestBody Order order) {
        Order result = orderService.updateOrder(order, (byte)OrderStatus.CANCEL.ordinal());
        orderService.addBackStorage(result);
        return RespBuilder.create(result.getStatus()==OrderStatus.CANCEL.ordinal(), VerifyRule.TRUE,
                "Order cancelled successfully","Operation fail");
    }
}
