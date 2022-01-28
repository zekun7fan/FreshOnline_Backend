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
            Class.forName("com.mysql.cj.jdbc.Driver");
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

        String sql_base = "select * from stocked_goods";

        Integer price_low = param.getInteger("price_low");
        Integer price_high = param.getInteger("price_high");
        Integer category_id = param.getInteger("category_id");
        String keyword = param.getString("keyword");
        String brands = param.getString("brands");
        /**
         * brands: ('brand1', 'brand2')
         */
        Integer sort_type = param.getInteger("sort_type");
        Integer page = param.getInteger("page");

        String sql_condition = " where true";
        if (price_low != null) sql_condition += " and price > " + price_low.toString();
        if (price_high != null) sql_condition += " and price < " + price_high.toString();
        if (category_id != null) sql_condition += " and category_id = " + category_id.toString();
        if (keyword != null) sql_condition += " and name like '%" + keyword + "%'";
        if (brands != null) sql_condition += " and brand in " + brands;
        if (sort_type != null){
            /**
             * @sort_type
             * 1: min(price, sale_price) 升序
             * 2: min(price, sale_price) 降序
             */
            if (sort_type == 1 || sort_type == 2) {
                sql_condition += " \norder by case \n" +
                        "\twhen COALESCE(sale_price, 10000) < price then sale_price\n" +
                        "\telse price\n" +
                        "end ";
                if (sort_type == 1) sql_condition += "asc ";
                else sql_condition += "desc ";
            }
            /**
             * 3: sales 升序
             * 4: sales 降序
             */
            if (sort_type == 3 || sort_type == 4) {
                sql_condition += " \norder by sales ";
                if (sort_type == 3) sql_condition += "asc ";
                else sql_condition += "desc ";
            }
        }
        if (page != null){
            Integer numPerRow = 5, rowsPerPage = 5;
            sql_condition += "\n limit " + ((page-1)*numPerRow*rowsPerPage) + "," + page*numPerRow*rowsPerPage;
        }

        String sql = sql_base + sql_condition + ";";
        System.out.println("sql = " + sql);

        List<StockedGoods> output = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                StockedGoods tmp = new StockedGoods();
                tmp.setId(result.getInt("id"));
                tmp.setName(result.getString("name"));
                tmp.setType(result.getByte("type"));
                tmp.setPrice(result.getBigDecimal("price"));
                tmp.setStorage(result.getBigDecimal("storage"));
                tmp.setSales(result.getBigDecimal("sales"));
                tmp.setDescription(result.getString("description"));
                tmp.setOnsale(result.getByte("onsale"));
                tmp.setBrand(result.getString("brand"));
                tmp.setCategoryId(result.getInt("category_id"));
                tmp.setIsNew(result.getByte("is_new"));
                tmp.setPic(result.getString("pic"));
                /**
                 * sale_price, rate, rate_count not included
                 */
                output.add(tmp);
                System.out.println("id=" + tmp.getId() + " name=" +  tmp.getName());
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
