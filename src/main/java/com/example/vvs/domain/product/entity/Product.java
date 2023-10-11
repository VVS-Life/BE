package com.example.vvs.domain.product.entity;

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

    @Lob
    private String content;

    @Column(length = 10, nullable = false)
    private int price;

    @Column(length = 50, nullable = false)
    private String category;

    @Builder
    public Product(Long id, String productName, String content, int price, String category) {
        this.id = id;
        this.productName = productName;
        this.content = content;
        this.price = price;
        this.category = category;
    }
}