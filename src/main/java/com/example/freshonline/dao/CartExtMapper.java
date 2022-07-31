package com.example.freshonline.dao;

import com.example.freshonline.model.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartExtMapper {


    List<Cart> selectByUserID(@Param("userId") Integer userId);
}
