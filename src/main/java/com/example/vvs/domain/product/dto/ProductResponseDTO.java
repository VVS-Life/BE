package com.example.vvs.domain.product.dto;

import com.example.vvs.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponseDTO {
    private Long id;
    private String productName;
    private String content;
    private int guarantee;
    private int price;
    private String category;

    @Builder
    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.content = product.getContent();
        this.guarantee = product.getGuarantee();
        this.price = product.getPrice();
        this.category = product.getCategory();
    }
}
