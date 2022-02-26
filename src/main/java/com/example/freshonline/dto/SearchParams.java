package com.example.freshonline.dto;


import com.example.freshonline.utils.ValidationChecker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@NoArgsConstructor
public class SearchParams implements VerifyRequestData<SearchParams> {

    @Getter @Setter
    public int price_low = -1;
    @Getter @Setter
    public int price_high = -1;
    @Getter @Setter
    public String brands;
    @Getter @Setter
    public int sort_type = -1;
    @Getter @Setter
    public String keyword;
    @Getter @Setter
    public int page = 1;
    @Getter @Setter
    public String category_id;
    @Getter @Setter
    public int num_per_row = 4, row_per_page = 5;
    @Setter
    public int item_low = -1;
    @Setter
    public int item_high = -1;

    @Override
    public SearchParams verifyParams(Map<String, String> param){
        SearchParams output = new SearchParams();
//        ValidationChecker vc = new ValidationChecker();
        try{
            if (param.containsKey("page")){
                int tmpPage = Integer.parseInt(param.get("page"));
                output.setPage(tmpPage > 0 ? tmpPage: 1);
            }
            if (param.containsKey("price_low")) {
                output.setPrice_low(Integer.parseInt(param.get("price_low")));
            }
            if (param.containsKey("price_high")) {
                output.setPrice_high(Integer.parseInt(param.get("price_high")));
            }
            if (param.containsKey("sort_type")) {
                output.setSort_type(Integer.parseInt(param.get("sort_type")));
            }
            if (param.containsKey("num_per_row")) {
                output.setNum_per_row(Integer.parseInt(param.get("num_per_row")));
            }
            if (param.containsKey("row_per_page")) {
                output.setRow_per_page(Integer.parseInt(param.get("row_per_page")));
            }
        } catch (NumberFormatException e){
            System.out.println("Searchparam, NumberFormatException");
            System.out.println(e.getMessage());
            throw e;
        }
        if (param.containsKey("brands")) {
            String brandsStr = param.get("brands");
            if (brandsStr==null || brandsStr.isBlank()) {
                output.setBrands(null);
            } else {
                try{
                    String[] brands_list = param.get("brands").split(",");
                    output.setBrands("('" + String.join("','", brands_list) + "')");
                } catch (Exception e) {output.setBrands(null);}
            }
        }
        if (param.containsKey("keyword")) {
            if (param.get("keyword") != null && param.get("keyword").length() > 0) {
                output.setKeyword(param.get("keyword"));
            } else { output.setKeyword(null); }
        }
        if (param.containsKey("category_id")) {
            if (param.get("category_id") != null && param.get("category_id").length() > 0){
                try {
                    String[] category_id_list = param.get("category_id").split(",");
                    output.setCategory_id("('" + String.join("','", category_id_list) + "')");
                } catch (Exception e) {output.setCategory_id(null);}
            } else { output.setCategory_id(null); }
        }
        output.computePage();
        return output;
    }

    private void computePage(){
        this.setItem_low((this.getPage() - 1) * this.getNum_per_row() * this.getRow_per_page());
        this.setItem_high(this.getPage() * this.getNum_per_row() * this.getRow_per_page());
    }

    @Override
    public String toString(){
        return  " category_id = " + category_id + " keyword = " + keyword
                + "\n selected brands = " + brands + " price_low = " + price_low + " price_high = " + price_high + " sort_type = " + sort_type
                + "\n page = " + page + " item_low = " + item_low + " item_high = " + item_high;
    }

}
