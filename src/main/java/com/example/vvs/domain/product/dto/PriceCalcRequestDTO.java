package com.example.vvs.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PriceCalcRequestDTO {
    private char gender;
    private String birth;

    @Builder
    public PriceCalcRequestDTO(char gender, String birth) {
        this.gender = gender;
        this.birth = birth;
    }
}
