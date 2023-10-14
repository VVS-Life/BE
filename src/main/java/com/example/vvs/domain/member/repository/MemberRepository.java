package com.example.vvs.domain.member.repository;

import com.example.vvs.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByAdminId(String adminId);

    Optional<Member> findByAdminId(String adminId);
}
