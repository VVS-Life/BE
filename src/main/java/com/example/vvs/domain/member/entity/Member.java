package com.example.vvs.domain.member.entity;

import com.example.vvs.domain.member.dto.MemberRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String adminId;
    @Column(length = 20)
    private String adminPassword;
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
    public Member(String adminId, String adminPassword, String userName, String birth, String email,
                  String address, String gender, String phoneNumber, String role) {
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
