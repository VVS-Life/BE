package com.example.vvs.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ProductDTO {
    private String productName;
    private String content;
    private int price;
    private String category;

    @Builder
    public ProductDTO(String productName, String content, int price, String category) {
        this.productName = productName;
        this.content = content;
        this.price = price;
        this.category = category;
    }

}
