package com.example.vvs.domain.member.service;

import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.dto.MemberRequestDTO;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import com.example.vvs.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.vvs.exception.ErrorHandling.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MessageDTO createMember(MemberRequestDTO memberRequestDTO) {

        if (memberRepository.existsByEmail(memberRequestDTO.getEmail())) {
            throw new ApiException(NO_UNIQUE_EMAIL);
        }

        if (memberRepository.existsByPhoneNumber(memberRequestDTO.getPhoneNumber())) {
            throw new ApiException(NO_UNIQUE_PHONENUMBER);
        }

        Member member = Member.builder()
                .userName(memberRequestDTO.getUserName())
                .birth(memberRequestDTO.getBirth())
                .email(memberRequestDTO.getEmail())
                .address(memberRequestDTO.getAddress())
                .gender(memberRequestDTO.getGender())
                .phoneNumber(memberRequestDTO.getPhoneNumber())
                .role(memberRequestDTO.getRole())
                .build();

        memberRepository.save(member);

        return MessageDTO.builder()
                .message("회원 가입 완료")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Transactional
    public MessageDTO loginAdmin(MemberRequestDTO memberRequestDTO) {
        Member member = memberRepository.findByAdminId(memberRequestDTO.getAdminId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_ADMIN_ID)
        );

        if (!member.getAdminPassword().equals(memberRequestDTO.getAdminPassword())) {
            throw new ApiException(NOT_MATCH_PASSWORD);
        }

        return MessageDTO.builder()
                .message("로그인 성공")
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}
