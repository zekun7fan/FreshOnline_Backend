package com.example.freshonline.dto;


import lombok.*;

import javax.validation.constraints.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParams implements VerifyRequestData {

    @PositiveOrZero
    private int price_low = 0;
    @PositiveOrZero
    private int price_high = 0;

    private List<String> brands = null;
    @Min(0) @Max(4)
    private int sort_type = 0;

    private String keyword = null;
    @Positive
    private int page = 1;

    private List<Integer> category_id = null;


    private int num_per_page = 20;
    private int item_low = 1;
    private int item_high = 20;

    public void computePage() {
        this.setItem_low((this.getPage() - 1) * this.getNum_per_page());
        this.setItem_high(this.getPage() * this.getNum_per_page());
    }

}
