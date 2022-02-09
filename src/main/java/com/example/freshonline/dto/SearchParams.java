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
        ValidationChecker vc = new ValidationChecker();
        if (param.containsKey("page")){ output.setPage(vc.str2int(param.get("page"), -1)); }
        if (param.containsKey("price_low")) { output.setPrice_low(vc.str2int(param.get("price_low"), -1)); }
        if (param.containsKey("price_high")) { output.setPrice_low(vc.str2int(param.get("price_high"), -1)); }
        if (param.containsKey("brands")) {
            try{
                String[] brands_list = param.get("brands").split(",");
                output.setBrands("('" + String.join("','", brands_list) + "')");
            } catch (Exception e) {output.setBrands(null);}
        }
        if (param.containsKey("keyword")) {
            if (param.get("keyword") != null && param.get("keyword").length() > 0) {
                this.setKeyword(param.get("keyword"));
            }
            else { this.setKeyword(null); }
        }
        if (param.containsKey("sort_type")) { output.setSort_type(vc.str2int(param.get("sort_type"), -1)); }
        if (param.containsKey("category_id")) {
            if (param.get("category_id") != null && param.get("category_id").length() > 0){
                try {
                    String[] category_id_list = param.get("category_id").split(",");
                    output.setCategory_id("('" + String.join("','", category_id_list) + "')");
                } catch (Exception e) {output.setCategory_id(null);}
            }
            else { output.setCategory_id(null); }
        }
        if (param.containsKey("num_per_row")) {
            output.setNum_per_row(vc.str2int(param.get("num_per_row"), output.getNum_per_row()));
        }
        if (param.containsKey("row_per_page")) {
            output.setRow_per_page(vc.str2int(param.get("row_per_page"), output.getRow_per_page()));
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
        return "price_low = " + price_low + " price_high = " + price_high
                + "\nbrands = " + brands + " keyword = " + keyword
                + "\nsort_type = " + sort_type + "\ncategory_id = " + category_id
                + "\npage = " + page + " item_low = " + item_low + " item_high = " + item_high;
    }

}
