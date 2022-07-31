package com.example.freshonline.event;


import org.springframework.context.ApplicationEvent;

import java.util.List;

public class FlushStorageEvent extends ApplicationEvent {

    private List<Integer> goodsIdList;

    public FlushStorageEvent(Object source, List<Integer> goodsIdList) {
        super(source);
        this.goodsIdList = goodsIdList;
    }

    public List<Integer> getGoodsIdList() {
        return goodsIdList;
    }
}
