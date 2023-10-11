package com.example.vvs.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDTO {

    private String userName;
    private String birth;
    private String email;
    private String address;
    private String gender;
    private String phoneNumber;
    private String role;

    @Builder
    public MemberResponseDTO(String userName, String birth, String email,
                             String address, String gender, String phoneNumber, String role) {
        this.userName = userName;
        this.birth = birth;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
