package com.example.vvs.domain.subscription.service;

import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import com.example.vvs.domain.product.entity.Product;
import com.example.vvs.domain.product.repository.ProductRepository;
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
import java.util.Optional;

import static com.example.vvs.exception.ErrorHandling.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {

    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(Long productId,
                                                                      SubscriptionRequestDTO subscriptionRequestDTO,
                                                                      Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ApiException(NOT_FOUND_PRODUCT)
        );

        Optional<Subscription> findSubscription = subscriptionRepository.findByProductIdAndMemberId(productId, memberId);
        if (findSubscription.isPresent()) {
            throw new ApiException(ErrorHandling.DUPLICATE_SUBSCRIPTION);
        }

        Subscription subscription = subscriptionRepository.save(Subscription.builder()
                .subscriptionRequestDTO(subscriptionRequestDTO)
                .product(product)
                .member(member)
                .build());

        return ResponseEntity.ok(SubscriptionResponseDTO.builder()
                .subscription(subscription)
                .product(product)
                .member(member)
                .build());
    }

    public ResponseEntity<SubscriptionResponseDTO> findSubscription(Member member, Long subscriptionId) {

        memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        Subscription subscriptionList = subscriptionRepository.findById(subscriptionId).orElseThrow(
                () -> new ApiException(EMPTY_SUBSCRIPTION)
        );

        return ResponseEntity.ok(SubscriptionResponseDTO.builder()
                .subscription(subscriptionList)
                .build());
    }

    public ResponseEntity<List<SubscriptionResponseDTO>> findAllSubscriptionList(Long memberId) {
        List<Subscription> subscriptionList = subscriptionRepository.findAllByMemberIdOrderByApplyDateDesc(memberId);
        List<SubscriptionResponseDTO> subscriptionResponseDTOList = new ArrayList<>();

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        System.out.println("member" + member.getNickname());

        for (Subscription subscription : subscriptionList) {

            Product product = productRepository.findById(subscription.getProduct().getId()).orElseThrow(
                    () -> new ApiException(NOT_FOUND_PRODUCT)
            );

            SubscriptionResponseDTO subscriptionResponseDTO = SubscriptionResponseDTO.builder()
                    .subscription(subscription)
                    .product(product)
                    .member(member)
                    .build();
            subscriptionResponseDTOList.add(subscriptionResponseDTO);
        }

        if (subscriptionResponseDTOList.isEmpty()) {
            throw new ApiException(EMPTY_SUBSCRIPTION);
        }

        return ResponseEntity.ok(subscriptionResponseDTOList);
    }

    public ResponseEntity<List<SubscriptionResponseDTO>> findAllSubscriptionAdminList(Long memberId) {

        isAdmin(memberId);

        List<Subscription> subscriptionList = subscriptionRepository.findAllByIsApprovalOrderByApplyDateDesc("가입 대기");
        List<SubscriptionResponseDTO> subscriptionResponseDTOList = new ArrayList<>();


        for (Subscription subscription : subscriptionList) {
            Member member = memberRepository.findById(subscription.getMember().getId()).orElseThrow(
                    () -> new ApiException(NOT_MATCH_USER)
            );

            Product product = productRepository.findById(subscription.getProduct().getId()).orElseThrow(
                    () -> new ApiException(NOT_FOUND_PRODUCT)
            );

            SubscriptionResponseDTO subscriptionResponseDTO = SubscriptionResponseDTO.builder()
                    .subscription(subscription)
                    .member(member)
                    .product(product)
                    .build();

            subscriptionResponseDTOList.add(subscriptionResponseDTO);
        }

        return ResponseEntity.ok(subscriptionResponseDTOList);
    }

    @Transactional
    public ResponseEntity<MessageDTO> updateAcceptSubscription(Long id, SubscriptionRequestDTO subscriptionRequestDTO, Long memberId) {

        isAdmin(memberId);

        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(
                () -> new ApiException(EMPTY_SUBSCRIPTION)
        );

        subscription.update(subscriptionRequestDTO);

        return ResponseEntity.ok(MessageDTO.builder()
                .message("가입 승인이 완료되었습니다")
                .statusCode(HttpStatus.OK.value()) // 200
                .build());
    }

    @Transactional
    public ResponseEntity<MessageDTO> updateRejectSubscription(Long id, SubscriptionRequestDTO subscriptionRequestDTO, Long memberId) {

        isAdmin(memberId);

        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(
                () -> new ApiException(EMPTY_SUBSCRIPTION)
        );

        subscription.update(subscriptionRequestDTO);

        return ResponseEntity.ok(MessageDTO.builder()
                .message("가입이 거절되었습니다")
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    private Member isAdmin(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        if (!member.getRole().equals("ADMIN")) {
            throw new ApiException(NOT_MATCH_AUTHORIZTION);
        }

        return member;
    }
}
