package com.example.freshonline.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.model.StockedGoods;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;


public class GoodsService {

    final BigDecimal MIN_PRICE = BigDecimal.valueOf(0), MAX_PRICE = BigDecimal.valueOf(10000);

    /**
     * @author Josh Sun
     * @param param
     * @return
     *   goods_list: List<StockedGoods> goods_list
     *   brand_list: new ArrayList<>(Set<String> brand_set)
     *   price_range: List<BigDecimal> price_range: Arrays.asList(minInit: MAX_PRICE, maxInit: MIN_PRICE)
     *   goods_total: Integer goods_total
     */
    public JSONObject getSearch(JSONObject param){
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
        String brands;
        try{
            String[] brands_list = param.getString("brands").split(",");
            brands = "('" + String.join("','", brands_list) + "')";
        } catch (NullPointerException e) {brands = null;}
        /**
         * brands: ('brand1', 'brand2')
         */
        Integer sort_type = param.getInteger("sort_type");
        Integer page = param.getInteger("page");

        String sql_condition = " where true";
        if (price_low != null) sql_condition += " and price > " + price_low;
        if (price_high != null) sql_condition += " and price < " + price_high;
        if (category_id != null) sql_condition += " and category_id = " + category_id;
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

        String sql_page;
        Integer numPerRow = 5, rowsPerPage = 5;
        Integer item_low = (page-1) * numPerRow * rowsPerPage, item_high = page * numPerRow * rowsPerPage;
        sql_page = "\n limit " + item_low + "," + item_high;

        String sql_with_page = sql_base + sql_condition + sql_page + ";";
        System.out.println("sql_with_page = " + sql_with_page);

        List<StockedGoods> goods_list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql_with_page);
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
                tmp.setSalePrice(result.getBigDecimal("sale_price"));
                tmp.setRate(result.getBigDecimal("rate"));
                tmp.setRateCount(result.getInt("rate_count"));
                goods_list.add(tmp);
            }
            result.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql_base_raw = "select count(*) from stocked_goods";
        String sql_raw = sql_base_raw + sql_condition + ";";
        System.out.println("sql_raw = " + sql_raw);
        Integer goods_total = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql_raw);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                goods_total = result.getInt(1);
            }
            result.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Set<String> brand_set = new HashSet<>();
        List<BigDecimal> price_range = new ArrayList<>(Arrays.asList(MAX_PRICE, MIN_PRICE));
        for (int i = 0; i < goods_list.size(); i ++) {
            StockedGoods tmp = goods_list.get(i);
            brand_set.add(tmp.getBrand());
            if (tmp.getOnsale() != 0){
                price_range.set(0, price_range.get(0).min(tmp.getSalePrice()));
                price_range.set(1, price_range.get(1).max(tmp.getSalePrice()));
            }
            else{
                price_range.set(0, price_range.get(0).min(tmp.getPrice()));
                price_range.set(1, price_range.get(1).max(tmp.getPrice()));
            }
        }

        System.out.println(price_range);
        System.out.println(brand_set);
        System.out.println(goods_total);

        JSONObject output = new JSONObject();
        output.put("goods_list", JSONObject.toJSONString(goods_list));
        output.put("price_range", JSONObject.toJSONString(price_range));
        output.put("brand_list", JSONObject.toJSONString(new ArrayList<>(brand_set)));
        output.put("goods_total", goods_total);

        return output;
    }

}
