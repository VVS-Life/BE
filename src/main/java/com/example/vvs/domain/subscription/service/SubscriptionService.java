package com.example.vvs.domain.subscription.service;

import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import com.example.vvs.domain.subscription.dto.SubscriptionRequestDTO;
import com.example.vvs.domain.subscription.dto.SubscriptionResponseDTO;
import com.example.vvs.domain.subscription.entity.Subscription;
import com.example.vvs.domain.subscription.repository.SubscriptionRepository;
import com.example.vvs.exception.ApiException;
import com.example.vvs.exception.ErrorHandling;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.vvs.exception.ErrorHandling.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {

    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public SubscriptionResponseDTO createSubscription(SubscriptionRequestDTO subscriptionRequestDTO) {

        Member member = memberRepository.findById(subscriptionRequestDTO.getMemberId()).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        Subscription subscription = subscriptionRepository.save(Subscription.builder()
                                                                            .subscriptionRequestDTO(subscriptionRequestDTO)
                                                                            .member(member)
                                                                            .build());
        return SubscriptionResponseDTO.builder()
                                    .subscription(subscription)
                                    .build();
    }
}
