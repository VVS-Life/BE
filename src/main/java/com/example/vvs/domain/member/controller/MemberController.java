package com.example.vvs.domain.member.controller;

import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.dto.MemberRequestDTO;
import com.example.vvs.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public MessageDTO postMember(MemberRequestDTO memberRequestDTO) {
        return memberService.createMember(memberRequestDTO);
    }

    @PostMapping("/join")
    public MessageDTO postAdminJoin(MemberRequestDTO memberRequestDTO) {
        return memberService.createAdmin(memberRequestDTO);
    }

    @PostMapping("/login")
    public MessageDTO postAdminLogin(MemberRequestDTO memberRequestDTO) {
        return memberService.loginAdmin(memberRequestDTO);
    }
}
