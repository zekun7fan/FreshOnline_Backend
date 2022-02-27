package com.example.freshonline.dto;

import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class SearchParamsUnitTest {

    private Map<String, String> packToMap(String price_low, String keyword,
                           String page, String category_id){
        Map<String, String> map = new HashMap<>();
        map.put("price_low", price_low);
        map.put("keyword", keyword);
        map.put("page", page);
        map.put("category_id", category_id);
        return map;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';',
            value={"1; 1; abc; abc; 1; 1; 123; ('123')",
                    "1; 1; abc; abc; 1; 1; 123,124; ('123','124')"})
    public void verifyParams(String price_low, String price_low_expected, String keyword, String keyword_expected,
                             String page, String page_expected, String category_id, String category_id_expected) {
        Map<String, String> map = packToMap(price_low, keyword, page, category_id);
        SearchParams param = new SearchParams();
        param = param.verifyParams(map);
        Assert.assertEquals(price_low_expected, ((Integer) param.getPrice_low()).toString());
        Assert.assertEquals(category_id_expected, param.getCategory_id());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "1.23"})
    public void verifyParamsNumberFormatException(String price_low) {
        Map<String, String> map = new HashMap<>();
        map.put("price_low", price_low);
        SearchParams param = new SearchParams();
        Throwable exception = Assert.assertThrows(NumberFormatException.class,
                ()-> { param.verifyParams(map); });
        System.out.println("verifyParamsNumberFormatException" + exception.getMessage());
    }
}