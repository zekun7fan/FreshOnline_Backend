package com.example.freshonline.dto;


import com.example.freshonline.exception.CustomException;
import com.example.freshonline.exception.SearchErrorCode;
import com.example.freshonline.utils.ValidationChecker;
import lombok.*;

import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParams implements VerifyRequestData {

    private int price_low = -1;
    private int price_high = -1;
    private String brands = null;
    private int sort_type = -1;
    private String keyword = null;
    private int page = 1;
    private String category_id = null;
    private int num_per_row = 4, row_per_page = 5;
    private int item_low = -1;
    private int item_high = -1;

    @Override
    public void verifyParams(Map<String, String> param) {
        try {
            int tmpPage = Integer.parseInt(param.get("page"));
            this.setPage(tmpPage > 0 ? tmpPage : 1);
        } catch (NumberFormatException ignored) {
            throw new CustomException(SearchErrorCode.SEARCH_PARAM_FORMAT_EXCEPTION);
        }
        try {
            this.setPrice_low(Integer.parseInt(param.get("price_low")));
        } catch (NumberFormatException ignored) {
            throw new CustomException(SearchErrorCode.SEARCH_PARAM_FORMAT_EXCEPTION);
        }
        try {
            this.setPrice_high(Integer.parseInt(param.get("price_high")));
        } catch (NumberFormatException ignored) {
            throw new CustomException(SearchErrorCode.SEARCH_PARAM_FORMAT_EXCEPTION);
        }
        try {
            this.setSort_type(Integer.parseInt(param.get("sort_type")));
        } catch (NumberFormatException ignored) {
            throw new CustomException(SearchErrorCode.SEARCH_PARAM_FORMAT_EXCEPTION);
        }
        try {
            this.setNum_per_row(Integer.parseInt(param.get("num_per_row")));
        } catch (NumberFormatException ignored) {
            throw new CustomException(SearchErrorCode.SEARCH_PARAM_FORMAT_EXCEPTION);
        }
        try {
            this.setRow_per_page(Integer.parseInt(param.get("row_per_page")));
        } catch (NumberFormatException ignored) {
            throw new CustomException(SearchErrorCode.SEARCH_PARAM_FORMAT_EXCEPTION);
        }

        String brandsStr = param.get("brands");
        if (brandsStr != null &&!brandsStr.isBlank()) {
            String[] brands_list = param.get("brands").split(",");
            this.setBrands("('" + String.join("','", brands_list) + "')");
        }
        String keywordStr = param.get("keyword");
        if (keywordStr != null && !keywordStr.isBlank()) {
            this.setKeyword(keywordStr);
        }
        String categoryIdStr = param.get("category_id");
        if (categoryIdStr != null) {
            String[] category_id_list = categoryIdStr.split(",");
            this.setCategory_id("('" + String.join("','", category_id_list) + "')");
        }
        this.computePage();
    }

    private void computePage() {
        this.setItem_low((this.getPage() - 1) * this.getNum_per_row() * this.getRow_per_page());
        this.setItem_high(this.getPage() * this.getNum_per_row() * this.getRow_per_page());
    }

    @Override
    public String toString() {
        return " category_id = " + category_id + " keyword = " + keyword
                + "\n selected brands = " + brands + " price_low = " + price_low + " price_high = " + price_high + " sort_type = " + sort_type
                + "\n page = " + page + " item_low = " + item_low + " item_high = " + item_high;
    }

}
