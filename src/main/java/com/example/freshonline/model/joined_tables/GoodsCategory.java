package com.example.freshonline.model.joined_tables;

import com.example.freshonline.model.Category;
import com.example.freshonline.model.StockedGoods;

public class GoodsCategory extends StockedGoods{
    private Category cate1;
    private Category cate2;
    private Category cate3;
    private int cateid;
    
    public GoodsCategory(Category cate1, Category cate2, Category cate3) {
        this.cate1 = cate1;
        this.cate2 = cate2;
        this.cate3 = cate3;
    }

    public int getCateid() {
        return cateid;
    }

    public void setCateid(int cateid) {
        this.cateid = cateid;
    }

    public GoodsCategory() {
        super();
    }
    public Category getcate1() {
        return cate1;
    }
    public void setcate1(Category cate1) {
        this.cate1 = cate1;
    }
    public Category getcate2() {
        return cate2;
    }
    public void setcate2(Category cate2) {
        this.cate2 = cate2;
    }
    public Category getcate3() {
        return cate3;
    }
    public void setcate3(Category cate3) {
        this.cate3 = cate3;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n" + cateid + cate1;
    }
    
}
