package com.example.freshonline.controller;

import com.example.freshonline.utils.ValidationChecker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {

    @GetMapping("/goods")
    public String getSearchTest(@RequestParam(value = "price_low", required = false) String price_low_req,
                                @RequestParam(value = "price_high", required = false) String price_high_req,
                                @RequestParam(value = "brands", required = false) String brands,
                                @RequestParam(value = "sort_type", required = false) String sort_type_req,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "page", required = false) String page_req,
                                @RequestParam(value = "category_id", required = false) String category_id_req){
        /*
        * @description: A test method for getSearch()
        * @author: Josh Sun
        * @Param: price_low:dec2, price_high:dec2, brands:String,
        * @Param: sort_type:int, keyword:String, page:int, category_id:int
        * @return String
        * */
        StringBuffer test = new StringBuffer();
        ValidationChecker vc = new ValidationChecker();

        Integer MIN_PRICE = 0, MAX_PRICE = 10000;
        Integer price_low = vc.str2int(price_low_req, MIN_PRICE);
        Integer price_high = vc.str2int(price_high_req, MAX_PRICE);
        test.append("price_low=" + price_low);
        test.append(",price_high=" + price_high);

        test.append(",brands=" + brands);

        Integer sort_type = vc.str2int(sort_type_req, 0);
        test.append(",sort_type=" + sort_type);

        test.append(",keyword=" + keyword);

        Integer page = vc.str2int(page_req, 1);
        test.append(",page=" + page);

        Integer category_id = vc.str2int(category_id_req, 0);
        test.append(",category_id=" + category_id);

        return test.toString();
    }

    @GetMapping("*")
    public void defaultMappingTest(){
        /*
        * @author: Josh Sun
        * @description: This is a default mapping*/
        System.out.println("default mapping");
    }
}
