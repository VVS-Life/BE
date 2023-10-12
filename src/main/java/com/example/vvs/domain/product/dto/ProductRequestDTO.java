package com.example.vvs.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRequestDTO {
    private String productName;
    private String content;
    private int price;
    private String category;

    @Builder
    public ProductRequestDTO(String productName, String content, int price, String category) {
        this.productName = productName;
        this.content = content;
        this.price = price;
        this.category = category;
    }
}
