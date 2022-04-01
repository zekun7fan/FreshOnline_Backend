package com.example.freshonline.listener;


import com.example.freshonline.dto.CreateOrderDetail;
import com.example.freshonline.event.CreateOrderEvent;
import com.example.freshonline.service.CartService;
import com.example.freshonline.service.OrderService;
import com.example.freshonline.service.SaledGoodsService;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class CustomEventListener {

    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    private OrderService orderService;

    @Autowired
    private SaledGoodsService saledGoodsService;

    @Autowired
    private CartService cartService;

    @EventListener
    public void handleCreateOrderEvent(CreateOrderEvent event) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                CreateOrderDetail detail = event.getDetail();

                // create order
                Integer orderId = orderService.createOrder(detail.getUserId(), detail.getLocation());

                // record saled goods
                saledGoodsService.addSaledGoods(orderId, detail.getOrderedGoods());

                // delete goods from cart
                ArrayList<Integer> goodsIdList = new ArrayList<>(detail.getOrderedGoods().keySet());
                if (goodsIdList.size() > 0){
                    cartService.batchDeleteFromCart(detail.getUserId(), goodsIdList);
                }
            }
        });
    }
}
