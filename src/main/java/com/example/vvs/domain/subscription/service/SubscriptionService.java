package com.example.vvs.domain.subscription.service;

import com.example.vvs.domain.common.MessageDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.vvs.exception.ErrorHandling.EMPTY_SUBSCRIPTION;
import static com.example.vvs.exception.ErrorHandling.NOT_MATCH_USER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {

    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(SubscriptionRequestDTO subscriptionRequestDTO) {

        Member member = memberRepository.findById(subscriptionRequestDTO.getMemberId()).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        Subscription subscription = subscriptionRepository.save(Subscription.builder()
                                                                            .subscriptionRequestDTO(subscriptionRequestDTO)
                                                                            .member(member)
                                                                            .build());
        return ResponseEntity.ok(SubscriptionResponseDTO.builder()
                                    .subscription(subscription)
                                    .build());
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

    @Transactional
    public MessageDTO updateSubscription(Long id, SubscriptionRequestDTO subscriptionRequestDTO) {

        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(
                () -> new ApiException(EMPTY_SUBSCRIPTION)
        );

        subscription.update(subscriptionRequestDTO);

        return MessageDTO.builder()
                .message("가입 승인이 완료되었습니다")
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}
