package com.example.vvs.domain.product.entity;

import com.example.vvs.domain.product.dto.ProductRequestDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String productName;

    @Column(nullable = false)
    private String content;

    @Column(length = 10, nullable = false)
    private int guarantee;

    @Column(length = 10, nullable = false)
    private int price;

    @Column(length = 50, nullable = false)
    private String category;

    @Builder
    public Product(Long id, String productName, String content, int guarantee, int price, String category) {
        this.id = id;
        this.productName = productName;
        this.content = content;
        this.guarantee = guarantee;
        this.price = price;
        this.category = category;
    }

    public void update(ProductRequestDTO productRequestDTO){
        productName = productRequestDTO.getProductName();
        content = productRequestDTO.getContent();
        price = productRequestDTO.getPrice();
        category = productRequestDTO.getCategory();
    }
}
