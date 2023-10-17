package com.example.vvs.domain.member.entity;

import com.example.vvs.domain.member.dto.JoinRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String loginPassword;
    @Column(length = 10, nullable = false)
    private String userName;
    @Column(length = 10, nullable = false)
    private String birth;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 50, nullable = false)
    private String address;
    @Column(length = 2, nullable = false)
    private String gender;
    @Column(length = 13, nullable = false)
    private String phoneNumber;
    @Column(length = 8, nullable = false)
    @ColumnDefault("\"member\"")
    private String role;

    @Builder
    public Member(JoinRequestDTO joinRequestDTO, String encodePassword, String role) {
        this.loginId = joinRequestDTO.getJoinId();
        this.loginPassword = encodePassword;
        this.userName = joinRequestDTO.getUserName();
        this.birth = joinRequestDTO.getBirth();
        this.email = joinRequestDTO.getEmail();
        this.address = joinRequestDTO.getAddress();
        this.gender = joinRequestDTO.getGender();
        this.phoneNumber = joinRequestDTO.getPhoneNumber();
        this.role = role;
    }

}
