package com.example.freshonline.dao;

import com.example.freshonline.dto.SearchParams;
import com.example.freshonline.dto.SearchResultInfo;
import com.example.freshonline.model.StockedGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockedGoodsExtMapper {



    List<StockedGoods> selectByOnsale();

    List<StockedGoods> selectByCategory(List<Integer> categoryIdList);

    List<StockedGoods> selectByFilter(@Param("param") SearchParams param);

    SearchResultInfo searchInfo(@Param("param") SearchParams param);



}
