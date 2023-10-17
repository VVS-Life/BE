package com.example.vvs.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class LoginRequestDTO {

    @Size(min = 6, max = 20, message = "ID는 6~20자리 사이로 입력하세요")
    private String loginId;
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리 사이로 입력하세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요")
    private String loginPassword;

    @Builder
    public LoginRequestDTO(String loginId, String loginPassword) {
        this.loginId = loginId;
        this.loginPassword = loginPassword;
    }
}
