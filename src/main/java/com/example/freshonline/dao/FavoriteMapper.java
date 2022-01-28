package com.example.freshonline.dao;

import com.example.freshonline.model.Favorite;
import com.example.freshonline.model.FavoriteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FavoriteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite
     *
     * @mbg.generated Fri Jan 28 11:53:04 EST 2022
     */
    long countByExample(FavoriteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite
     *
     * @mbg.generated Fri Jan 28 11:53:04 EST 2022
     */
    int deleteByExample(FavoriteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite
     *
     * @mbg.generated Fri Jan 28 11:53:04 EST 2022
     */
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite
     *
     * @mbg.generated Fri Jan 28 11:53:04 EST 2022
     */
    int insert(Favorite record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite
     *
     * @mbg.generated Fri Jan 28 11:53:04 EST 2022
     */
    int insertSelective(Favorite record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite
     *
     * @mbg.generated Fri Jan 28 11:53:04 EST 2022
     */
    List<Favorite> selectByExample(FavoriteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite
     *
     * @mbg.generated Fri Jan 28 11:53:04 EST 2022
     */
    int updateByExampleSelective(@Param("record") Favorite record, @Param("example") FavoriteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite
     *
     * @mbg.generated Fri Jan 28 11:53:04 EST 2022
     */
    int updateByExample(@Param("record") Favorite record, @Param("example") FavoriteExample example);
}