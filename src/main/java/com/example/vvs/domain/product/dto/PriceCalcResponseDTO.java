package com.example.vvs.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PriceCalcResponseDTO {

    private Integer price;

    @Builder
    public PriceCalcResponseDTO(Integer price) {
        this.price = price;
    }
}
