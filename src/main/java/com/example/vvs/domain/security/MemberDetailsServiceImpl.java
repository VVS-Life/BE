package com.example.vvs.domain.security;

import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {

        Member member = memberRepository.findByNickname(nickName).orElseThrow(
                () -> new UsernameNotFoundException("사용자를 찾을수 없습니다.")
        );

        return new MemberDetailsImpl(member, member.getUserName());
    }
}