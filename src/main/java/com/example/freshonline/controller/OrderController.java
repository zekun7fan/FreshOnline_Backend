package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.enums.OrderStatus;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.event.CustomEventPublisher;
import com.example.freshonline.model.Order;
import com.example.freshonline.model.User;
import com.example.freshonline.service.OrderService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomEventPublisher publisher;


    @PostMapping("/payOrder")
    @PreAuthorize("checkUserId(#order.userId, #session)")
    public JSONObject payOrder(@RequestBody Order order, HttpSession session) {
        order.setPayTime(LocalDateTime.now());
        Order result = orderService.updateOrder(order, (byte)OrderStatus.PAID.ordinal());
        boolean res = false;
        if (result != null && result.getStatus()==OrderStatus.PAID.ordinal()){
            res = true;
            publisher.publishDeliveryOrderEvent(result.getOrderId(), 60000);
            publisher.publishFinishOrderEvent(result.getOrderId(), 180000);
        }
        return RespBuilder.create(res, VerifyRule.TRUE,
                "Order paid successfully","Payment failed");
    }


    @PostMapping("/cancelOrder")
    @PreAuthorize("checkUserId(#order.userId, #session)")
    public JSONObject cancelOrder(@RequestBody Order order, HttpSession session) {
        Order result = orderService.updateOrder(order, (byte)OrderStatus.CANCELLED.ordinal());
        orderService.addBackStorage(result);
        return RespBuilder.create(result.getStatus()==OrderStatus.CANCELLED.ordinal(), VerifyRule.TRUE,
                "Order cancelled successfully","Operation fail");
    }
}
