package com.example.vvs.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberRequestDTO {

    @Size(min = 6, max = 20, message = "ID는 6~20자리 사이로 입력하세요")
    private String adminId;
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리 사이로 입력하세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요")
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
        this.role = role == null ? "member" : role;
    }
}
