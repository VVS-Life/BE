package com.example.vvs.domain.member.controller;

import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.dto.JoinRequestDTO;
import com.example.vvs.domain.member.dto.LoginRequestDTO;
import com.example.vvs.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join/member")
    public ResponseEntity<MessageDTO> postMember(@Valid @RequestBody JoinRequestDTO joinRequestDTO) {
        return memberService.createMember(joinRequestDTO);
    }

    @PostMapping("/join/admin")
    public ResponseEntity<MessageDTO> postAdmin(@Valid @RequestBody JoinRequestDTO joinRequestDTO) {
        return memberService.createAdmin(joinRequestDTO);
    }

    @PostMapping("/login/member")
    public ResponseEntity<MessageDTO> postLogin(@RequestBody LoginRequestDTO loginRequestDTO,
                                HttpServletResponse response) {
        return memberService.login(loginRequestDTO, response);
    }

    @PostMapping("/login/admin")
    public ResponseEntity<MessageDTO> postAdminLogin(@RequestBody LoginRequestDTO loginRequestDTO,
                                     HttpServletResponse response) {
        return memberService.adminLogin(loginRequestDTO, response);
    }

}