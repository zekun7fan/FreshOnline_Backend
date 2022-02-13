package com.example.freshonline.dao;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.freshonline.model.joined_tables.GoodsCategory;

import java.math.BigDecimal;
import java.util.List;

import com.example.freshonline.model.Cart;

@SpringBootTest
@MapperScan("com.example.freshonline.dao")
public class CartTest {
    
    
    @Autowired
    private CartMapper cartMapper;
    
    
    @Test
    public void test1(){
        Cart record = new Cart();
        BigDecimal count = new BigDecimal("1");
        record.setCount(count);
        record.setGoodsId(1);
        record.setUserId(1);
        cartMapper.insert(record);
    }


    @Test
    public void test2(){
        List<Cart> l = cartMapper.selectByUserID(5);
        System.err.println(l.get(0).getStockedGoods().getName());
    }

    @Test
    public void test3(){
        Cart c = new Cart();
        c.setGoodsId(9);
        c.setUserId(5);
        BigDecimal count = new BigDecimal("15.12");
        c.setCount(count);
        cartMapper.updateByPrimaryKeySelective(c);
    }
}
