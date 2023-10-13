package com.example.vvs.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInsuranceInfoDTO {
    private char gender;
    private String birth;

    @Builder
    public UserInsuranceInfoDTO(char gender, String birth) {
        this.gender = gender;
        this.birth = birth;
    }
}
