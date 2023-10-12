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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.vvs.exception.ErrorHandling.*;

@Slf4j
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

    public List<SubscriptionResponseDTO> findAllSubscription(Long memberId) {
        List<Subscription> subscriptionList = subscriptionRepository.findByMemberId(memberId);
        List<SubscriptionResponseDTO> subscriptionResponseDTOList = new ArrayList<>();

        if (subscriptionList.isEmpty()) {
            throw new ApiException(EMPTY_SUBSCRIPTION);
        }

        for (Subscription subscription : subscriptionList) {
            SubscriptionResponseDTO subscriptionResponseDTO = SubscriptionResponseDTO.builder()
                    .subscription(subscription)
                    .build();
            subscriptionResponseDTOList.add(subscriptionResponseDTO);
        }

        return subscriptionResponseDTOList;
    }

}
