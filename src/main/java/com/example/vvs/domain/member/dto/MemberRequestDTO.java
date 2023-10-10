package com.example.vvs.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class MemberRequestDTO {

    private String adminId;
    private String adminPassword;
    @NotNull(message = "이름을 입력하세요")
    private String userName;
    @NotNull(message = "생년월일을 입력하세요")
    private String birth;
    @NotNull(message = "이메일을 입력하세요")
    @Email
    private String email;
    @NotNull(message = "주소를 입력하세요")
    private String address;
    @NotNull(message = "성별을 선택하세요")
    private String gender;
    @NotNull(message = "핸드폰 번호를 입력하세요")
    private String phoneNumber;
    private String role;

    @Builder
    public MemberRequestDTO(String adminId, String adminPassword, String userName, String birth,
                            String email, String address, String gender, String phoneNumber, String role) {
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.userName = userName;
        this.birth = birth;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
