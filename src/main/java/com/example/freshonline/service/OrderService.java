package com.example.freshonline.service;

import com.example.freshonline.dao.OrderDetailMapper;
import com.example.freshonline.dao.OrderMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.dto.OrderDetail;
import com.example.freshonline.dto.SaledGoodsDetail;
import com.example.freshonline.enums.OrderStatus;
import com.example.freshonline.model.Order;
import com.example.freshonline.model.StockedGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    public List<OrderDetail> getOrderByUserId(Integer userId, Integer position){
        return orderDetailMapper.selectByUserId(userId,position);
    }


    public Integer createOrder(Integer userId, String location){
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus((byte) OrderStatus.CREATED.getStatus());
        order.setLocation(location);
        orderMapper.insertSelective(order);
        return order.getOrderId();
    }

    public Order updateOrder(Order order, byte status){
        order.setStatus(status);
        orderMapper.updateByPrimaryKeySelective(order);
        return order;
    }

    public void addBackStorage(Order order){
        List<OrderDetail> ods = orderDetailMapper.selectByOrderId(order.getOrderId());
        for(OrderDetail od:ods){
            List<SaledGoodsDetail> sgs = od.getGoodsList();
            for(SaledGoodsDetail sg:sgs){
                StockedGoods goods = new StockedGoods();
                goods.setId(sg.getGoodsId());
                goods.setStorage(sg.getStorage().add(sg.getCount()));
                stockedGoodsMapper.updateByPrimaryKeySelective(goods);
            }
        }
    }


    public void deliveryOrder(Integer orderId){
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus((byte)OrderStatus.IN_DELIVERING.getStatus());
        orderMapper.updateByPrimaryKeySelective(order);
    }


    public void finishOrder(Integer orderId){
        Order order = new Order();
        order.setOrderId(orderId);
        order.setFinishTime(LocalDateTime.now());
        order.setStatus((byte)OrderStatus.FINISHED.getStatus());
        orderMapper.updateByPrimaryKeySelective(order);
    }
}
