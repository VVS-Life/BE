package com.example.vvs.domain.member.repository;

import com.example.vvs.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByLoginId(String loginId);

    Optional<Member> findByLoginId(String adminId);
}
