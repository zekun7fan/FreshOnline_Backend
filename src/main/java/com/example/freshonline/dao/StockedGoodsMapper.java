package com.example.freshonline.dao;

import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.model.StockedGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockedGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    long countByExample(StockedGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int deleteByExample(StockedGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int insert(StockedGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int insertSelective(StockedGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    List<StockedGoods> selectByExampleWithBLOBs(StockedGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    List<StockedGoods> selectByExample(StockedGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    StockedGoods selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByExampleSelective(@Param("record") StockedGoods record, @Param("example") StockedGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByExampleWithBLOBs(@Param("record") StockedGoods record, @Param("example") StockedGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByExample(@Param("record") StockedGoods record, @Param("example") StockedGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByPrimaryKeySelective(StockedGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByPrimaryKeyWithBLOBs(StockedGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stocked_goods
     *
     * @mbg.generated Sun Jul 17 14:43:06 EDT 2022
     */
    int updateByPrimaryKey(StockedGoods record);
}