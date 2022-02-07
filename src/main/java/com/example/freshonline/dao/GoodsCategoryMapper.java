package com.example.freshonline.dao;
import com.example.freshonline.model.joined_tables.GoodsCategory;

import org.apache.ibatis.annotations.Param;

public interface GoodsCategoryMapper {
    GoodsCategory selectByGoodsID(@Param("goodsId") Integer goodsId);
}