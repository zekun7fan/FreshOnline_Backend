package com.example.freshonline.controller;



import com.example.freshonline.utils.ValidationChecker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Josh Sun
 */
@RestController
public class GoodsController {


    /**
     * @author Josh Sun
     * @param price_low_req String
     * @param price_high_req String
     * @param brands String
     * @param sort_type_req String
     * @param keyword String
     * @param page_req String
     * @param category_id_req String
     * @return String
     */
    @GetMapping("/goods")
    public String getSearchTest(@RequestParam(value = "price_low", required = false) String price_low_req,
                                @RequestParam(value = "price_high", required = false) String price_high_req,
                                @RequestParam(value = "brands_id", required = false) String brands,
                                @RequestParam(value = "sort_type", required = false) String sort_type_req,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "page", required = false) String page_req,
                                @RequestParam(value = "category_id", required = false) String category_id_req){
        StringBuilder test = new StringBuilder();
        ValidationChecker vc = new ValidationChecker();

        Integer MIN_PRICE = 0, MAX_PRICE = 10000;
        Integer price_low = vc.str2int(price_low_req, MIN_PRICE);
        Integer price_high = vc.str2int(price_high_req, MAX_PRICE);
        test.append("price_low=").append(price_low);
        test.append(",price_high=").append(price_high);

        test.append(",brands=").append(brands);

        Integer sort_type = vc.str2int(sort_type_req, 0);
        test.append(",sort_type=").append(sort_type);

        test.append(",keyword=").append(keyword);

        Integer page = vc.str2int(page_req, 1);
        test.append(",page=").append(page);

        Integer category_id = vc.str2int(category_id_req, 0);
        test.append(",category_id=").append(category_id);

        return test.toString();
    }

    /**
     * @author Josh Sun
     */
    @GetMapping("*")
    public void defaultMappingTest(){
        System.out.println("default mapping");
    }
}
