package com.example.freshonline.service;

import com.example.freshonline.dao.OrderDetailMapper;
import com.example.freshonline.dao.OrderMapper;
import com.example.freshonline.dao.SaledGoodsMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.dto.OrderDetail;
import com.example.freshonline.dto.SaledGoodsDetail;
import com.example.freshonline.enums.OrderStatus;
import com.example.freshonline.event.CustomEventPublisher;
import com.example.freshonline.model.Order;
import com.example.freshonline.model.SaledGoods;
import com.example.freshonline.model.SaledGoodsExample;
import com.example.freshonline.model.StockedGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;


    @Autowired
    private SaledGoodsMapper saledGoodsMapper;


    @Autowired
    private StockedGoodsService stockedGoodsService;

    @Autowired
    private CustomEventPublisher publisher;

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
        int cnt = orderMapper.updateByPrimaryKeySelective(order);
        return cnt > 0 ? order : null;
    }

    public void addBackStorage(Order order){
        SaledGoodsExample example = new SaledGoodsExample();
        example.createCriteria().andOrderIdEqualTo(order.getOrderId());
        List<SaledGoods> goods = saledGoodsMapper.selectByExample(example);
        // just incr storage of goods in redis is enough
        for (SaledGoods g : goods) {
            stockedGoodsService.incrStorage(g.getGoodsId(), g.getCount());
        }
        List<Integer> list = goods.stream().map(SaledGoods::getGoodsId).collect(Collectors.toList());
        publisher.publishFlushStorageEvent(list);

    }



    public void deliveryOrder(Integer orderId){
        System.out.println("ddd2" + LocalDateTime.now());
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
