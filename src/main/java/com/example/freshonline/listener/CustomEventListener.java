package com.example.freshonline.listener;


import com.example.freshonline.dto.CreateOrderDetail;
import com.example.freshonline.event.CreateOrderEvent;
import com.example.freshonline.event.DeliveryOrderEvent;
import com.example.freshonline.event.FinishOrderEvent;
import com.example.freshonline.event.FlushStorageEvent;
import com.example.freshonline.service.CartService;
import com.example.freshonline.service.OrderService;
import com.example.freshonline.service.SaledGoodsService;
import com.example.freshonline.service.StockedGoodsService;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    private StockedGoodsService stockedGoodsService;

    @Autowired
    private CartService cartService;

    @Autowired
    private TaskScheduler taskScheduler;

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


    @EventListener
    public void handleDeliverOrderEvent(DeliveryOrderEvent event){
        taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                orderService.deliveryOrder(event.getOrderId());
            }
        }, new Date(System.currentTimeMillis() + event.getDelay()));
    }


    @EventListener
    public void handleFinishOrderEvent(FinishOrderEvent event){
        taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                orderService.finishOrder(event.getOrderId());
            }
        }, new Date(System.currentTimeMillis() + event.getDelay()));
    }


    @EventListener
    public void handleFlushStorageEvent(FlushStorageEvent event) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (Integer id : event.getGoodsIdList()) {
                    stockedGoodsService.flushRedisStorageToDb(id);
                }
            }
        });
    }

}
