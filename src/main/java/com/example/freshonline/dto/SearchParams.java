package com.example.freshonline.dto;


import lombok.*;

import javax.validation.constraints.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParams implements VerifyRequestData {

    @NotNull @PositiveOrZero
    private int price_low = 0;
    @NotNull @PositiveOrZero
    private int price_high = 0;
    @NotNull
    private List<String> brands = null;
    @NotNull @Min(0) @Max(4)
    private int sort_type = 0;
    @NotNull
    private String keyword = null;
    @NotNull @Positive
    private int page = 1;
    @NotNull
    private List<Integer> category_id = null;
    @NotNull @Positive
    private int num_per_page = 20;

//    private String brands_valid = null;
//    private String category_id_valid = null;
    private int item_low = 1;
    private int item_high = 20;

//    @Override
//    public void verifyParams(Map<String, String> param) {
//        try {
//            int tmpPage = Integer.parseInt(param.get("page"));
//            this.setPage(tmpPage > 0 ? tmpPage : 1);
//        } catch (NumberFormatException ignored) {
////            throw new CustomException(CustomizeErrorCode.SEARCH_PARAM_FORMAT_EXCEPTION);
//            // this can't be added, if "page" is not in param, then throws exception?
//        }
//        try {
//            this.setPrice_low(Integer.parseInt(param.get("price_low")));
//        } catch (NumberFormatException ignored) {
//        }
//        try {
//            this.setPrice_high(Integer.parseInt(param.get("price_high")));
//        } catch (NumberFormatException ignored) {
//        }
//        try {
//            this.setSort_type(Integer.parseInt(param.get("sort_type")));
//        } catch (NumberFormatException ignored) {
//
//        }
//        try {
//            this.setNum_per_page(Integer.parseInt(param.get("num_per_page")));
//        } catch (NumberFormatException ignored) {
//        }
//
//        String brandsStr = param.get("brands");
//        if (brandsStr != null &&!brandsStr.isBlank()) {
//            String[] brands_list = param.get("brands").split(",");
//            this.setBrands("('" + String.join("','", brands_list) + "')");
//        }
//        String keywordStr = param.get("keyword");
//        if (keywordStr != null && !keywordStr.isBlank()) {
//            this.setKeyword(keywordStr);
//        }
//        String categoryIdStr = param.get("category_id");
//        if (categoryIdStr != null) {
//            String[] category_id_list = categoryIdStr.split(",");
//            this.setCategory_id("('" + String.join("','", category_id_list) + "')");
//        }
//        this.computePage();
//    }

    public void computePage() {
        this.setItem_low((this.getPage() - 1) * this.getNum_per_page());
        this.setItem_high(this.getPage() * this.getNum_per_page());
    }

//    public void convert(){
//        if (this.getBrands() != null && this.getBrands().length() != 0) {
//            String[] brands_list = this.getBrands().split(",");
//            this.setBrands_valid("('" + String.join("','", brands_list) + "')");
//        }
//        if (this.getCategory_id() != null && this.getCategory_id().length() != 0) {
//            String[] category_list  = this.getCategory_id().split(",");
//            if (Integer.parseInt(category_list[0]) != 0) {
//                this.setCategory_id_valid("(" + this.getCategory_id() + ")");
//            }
//        }
//        this.computePage();
//    }

}
