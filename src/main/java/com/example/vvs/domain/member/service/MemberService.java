package com.example.vvs.domain.member.service;

import com.example.vvs.domain.auth.JwtUtil;
import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.dto.JoinRequestDTO;
import com.example.vvs.domain.member.dto.LoginRequestDTO;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import com.example.vvs.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static com.example.vvs.exception.ErrorHandling.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MessageDTO createMember(JoinRequestDTO joinRequestDTO) {

        String encodePassword = passwordEncoder.encode(joinRequestDTO.getPassword());

        if (memberRepository.existsByNickname(joinRequestDTO.getNickname())) {
            throw new ApiException(NO_UNIQUE_LOGIN_ID);
        }

        if (memberRepository.existsByPhoneNumber(joinRequestDTO.getPhoneNumber())) {
            throw new ApiException(NO_UNIQUE_PHONENUMBER);
        }

        String role = joinRequestDTO.getRole() == null ? "MEMBER" : joinRequestDTO.getRole();
        if (!(role.equals("MEMBER") || role.equals("ADMIN"))) {
            throw new ApiException(NOT_EXISTENCE_ROLE);
        }

        Member member = Member.builder()
                .joinRequestDTO(joinRequestDTO)
                .encodePassword(encodePassword)
                .role(role)
                .build();

        memberRepository.save(member);

        return MessageDTO.builder()
                .message("회원 가입 완료")
                .statusCode(HttpStatus.ACCEPTED.value())
                .build();
    }

    @Transactional
    public MessageDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
      
        Member member = memberRepository.findByNickname(loginRequestDTO.getNickname()).orElseThrow(
                () -> new ApiException(NOT_FOUND_ADMIN_ID)
        );

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(loginRequestDTO.getPassword(), member.getPassword())) {
            throw new ApiException(NOT_MATCH_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getNickname(), member.getRole()));

        return MessageDTO.builder()
                .message("로그인 성공")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Transactional
    public MessageDTO adminLogin(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {

        Member member = memberRepository.findByNickname(loginRequestDTO.getNickname()).orElseThrow(
                () -> new ApiException(NOT_FOUND_ADMIN_ID)
        );

        if (!loginRequestDTO.getPassword().equals("ADMIN")) {
            throw new ApiException(NOT_MATCH_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getNickname(), member.getRole()));


        return MessageDTO.builder()
                .message("로그인 성공")
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}