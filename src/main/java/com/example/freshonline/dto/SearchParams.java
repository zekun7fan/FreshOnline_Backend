package com.example.freshonline.dto;


import com.example.freshonline.utils.ValidationChecker;

import java.util.Map;

public class SearchParams {

    public int price_low = -1;
    public int price_high = -1;
    public String brands;
    public int sort_type = -1;
    public String keyword;
    public int page = 1;
    public int category_id = -1;
    public int num_per_row = 4, row_per_page = 5;
    public int item_low = -1;
    public int item_high = -1;

    public int getPrice_low() {
        return price_low;
    }

    public void setPrice_low(int price_low) {
        this.price_low = price_low;
    }

    public int getPrice_high() {
        return price_high;
    }

    public void setPrice_high(int price_high) {
        this.price_high = price_high;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public int getSort_type() {
        return sort_type;
    }

    public void setSort_type(int sort_type) {
        this.sort_type = sort_type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getNum_per_row() {
        return num_per_row;
    }

    public void setNum_per_row(int num_per_row) {
        this.num_per_row = num_per_row;
    }

    public int getRow_per_page() {
        return row_per_page;
    }

    public void setRow_per_page(int row_per_page) {
        this.row_per_page = row_per_page;
    }

    public int getItem_low() {
        return item_low;
    }

    public void setItem_low(int item_low) {
        this.item_low = item_low;
    }

    public int getItem_high() {
        return item_high;
    }

    public void setItem_high(int item_high) {
        this.item_high = item_high;
    }

    public SearchParams(Map<String, String> param) {
        ValidationChecker vc = new ValidationChecker();
        if (param.containsKey("page")){
            this.page = vc.str2int(param.get("page"), -1);
        }
        if (param.containsKey("price_low")) {
            this.price_low = vc.str2int(param.get("price_low"), -1);
        }
        if (param.containsKey("price_high")) {
            this.price_high = vc.str2int(param.get("price_high"), -1);
        }
        if (param.containsKey("brands")) {
            String[] brands_list = param.get("brands").split(",");
            this.brands = "('" + String.join("','", brands_list) + "')";
        }
        if (param.containsKey("keyword")) {
            this.keyword = param.get("keyword");
        }
        if (param.containsKey("sort_type")) {
            this.sort_type = vc.str2int(param.get("sort_type"), -1);
        }
        if (param.containsKey("category_id")) {
            this.category_id = vc.str2int(param.get("category_id"), -1);
        }
        if (param.containsKey("num_per_row")) {
            this.num_per_row = vc.str2int(param.get("num_per_row"), this.num_per_row);
        }
        if (param.containsKey("row_per_page")) {
            this.row_per_page = vc.str2int(param.get("row_per_page"), this.row_per_page);
        }
        this.computePage();
    }

    private void computePage(){
        this.item_low = (this.page - 1) * this.num_per_row * this.row_per_page;
        this.item_high = (this.page) * this.num_per_row * this.row_per_page;
    }

    @Override
    public String toString(){
        return "price_low = " + price_low + " price_high = " + price_high
                + "\nbrands = " + brands + " keyword = " + keyword
                + "\nsort_type = " + sort_type + "\ncategory_id = " + category_id
                + "\npage = " + page + " item_low = " + item_low + " item_high = " + item_high;
    }

}
