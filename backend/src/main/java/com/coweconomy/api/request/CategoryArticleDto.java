package com.coweconomy.api.request;

import lombok.Data;
import lombok.Getter;

@Data
public class CategoryArticleDto {
    private Long finance;
    private Long stock;
    private Long industry;
    private Long venture;
    private Long estate;
    private Long worldwide;
    private Long life;
    private Long common;
    public Long[] setCategoryLast(CategoryArticleDto categoryLast){
        Long[] category = new Long[8];
        category[0] = categoryLast.getFinance();
        category[1] = categoryLast.getStock();
        category[2] = categoryLast.getIndustry();
        category[3] = categoryLast.getVenture();
        category[4] = categoryLast.getEstate();
        category[5] = categoryLast.getWorldwide();
        category[6] = categoryLast.getLife();
        category[7] = categoryLast.getCommon();
        return category;
    }
}
