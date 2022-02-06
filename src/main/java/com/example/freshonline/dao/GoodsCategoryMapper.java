package com.example.freshonline.dao;
import com.example.freshonline.model.joined_tables.GoodsCategory;

public interface GoodsCategoryMapper {
    GoodsCategory selectByGoodsID(Integer id);
}