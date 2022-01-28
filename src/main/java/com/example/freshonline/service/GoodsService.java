package com.example.freshonline.service;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.model.StockedGoods;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.ResultSet;
import java.util.List;


public class GoodsService {

    /**
     * @author Josh Sun
     * @param param
     * @return
     */
    public List<StockedGoods> getSearch(JSONObject param){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Properties pros;
        Connection conn = null;
        try {
            pros = new Properties();
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            Class.forName(pros.getProperty("spring.datasource.driver-class-name"));
            conn = DriverManager.getConnection(pros.getProperty("spring.datasource.url"), pros.getProperty("spring.datasource.username"), pros.getProperty("spring.datasource.password"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        List<StockedGoods> output = new ArrayList<>();
        String sql_base = "select * from stocked_goods";
        String sql_condition = "where true";

        Integer price_low = param.getInteger("price_low");
        if (price_low != null) {
            sql_condition += " and price > " + price_low.toString();
        }
        Integer price_high = param.getInteger("price_high");
        if (price_high != null) {
            sql_condition += " and price < " + price_high.toString();
        }
        String brands = param.getString("brands").toString();
        if (brands != null){
            sql_condition += " and brands like %" + brands + "%";
        }
        Integer sort_type = param.getInteger("sort_type");
        if (sort_type != null){
            /**
             * @TODO sort_type
             */
        }
        Integer page = param.getInteger("page");
        if (page != null){
            /**
             * @TODO 分页
             */
        }
        Integer category_id = param.getInteger("category_id");
        if (category_id != null){
            sql_condition += " and category_id = " + category_id.toString();
        }

        String sql = sql_base + sql_condition;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                StockedGoods tmp = new StockedGoods();
                tmp.setId(result.getInt("id"));
                tmp.setName(result.getString("name"));
                /**
                 * @TODO Type is a String? or TinyInt?
                 */
                tmp.setType(result.getString("type"));
                tmp.setPrice(result.getBigDecimal("price"));
                tmp.setStorage(result.getBigDecimal("storage"));
                tmp.setSales(result.getBigDecimal("sales"));
                tmp.setDescription(result.getString("description"));
                /**
                 * Byte vs TinyInt
                 */
                tmp.setOnsale(result.getByte("onsale"));
                tmp.setBrand(result.getString("brand"));
                tmp.setCategoryId(result.getInt("category_id"));
                /**
                 * Byte vs TinyInt
                 */
                tmp.setIsNew(result.getByte("is_new"));
                tmp.setPic(result.getString("pic"));
                /**
                 * sale_price, rate, rate_count not included
                 */
                output.add(tmp);
            }
            result.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }

}
