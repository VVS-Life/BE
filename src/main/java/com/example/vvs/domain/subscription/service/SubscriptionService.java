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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                                                                      Member member) {

        Member findMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ApiException(NOT_FOUND_PRODUCT)
        );

        Optional<Subscription> findSubscription = subscriptionRepository.findByProductId(productId);
        if (findSubscription.isPresent()) {
            throw new ApiException(ErrorHandling.DUPLICATE_SUBSCRIPTION);
        }

        Subscription subscription = subscriptionRepository.save(Subscription.builder()
                .subscriptionRequestDTO(subscriptionRequestDTO)
                .product(product)
                .member(findMember)
                .build());

        return ResponseEntity.ok(SubscriptionResponseDTO.builder()
                .subscription(subscription)
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

    public ResponseEntity<Page<SubscriptionResponseDTO>> findAllSubscriptionPage(Long memberId, Pageable pageable) {
        Page<SubscriptionResponseDTO> subscriptionResponseDTOPage = subscriptionRepository.findAllByIdOrderByIdDesc(memberId, pageable).map(
                (Subscription subscription) -> SubscriptionResponseDTO.builder().subscription(subscription).build()
        );
        if (subscriptionResponseDTOPage.isEmpty()) {
            throw new ApiException(EMPTY_SUBSCRIPTION);
        }
        return ResponseEntity.ok(subscriptionResponseDTOPage);
    }

    public ResponseEntity<Page<SubscriptionResponseDTO>> findAllSubscriptionAdminPage(Member member, Pageable pageable) {

        Member findMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        if (!findMember.getRole().equals("ADMIN")) {
            throw new ApiException(NOT_MATCH_AUTHORIZTION);
        }

        Page<SubscriptionResponseDTO> subscriptionResponseDTOPage = subscriptionRepository.findAllByOrderByIdDesc(pageable).map(
                (Subscription subscription) -> SubscriptionResponseDTO.builder().subscription(subscription).build()
        );

        if (subscriptionResponseDTOPage.isEmpty()) {
            throw new ApiException(EMPTY_SUBSCRIPTION);
        }
        return ResponseEntity.ok(subscriptionResponseDTOPage);
    }

    @Transactional
    public ResponseEntity<MessageDTO> updateSubscription(Long id, SubscriptionRequestDTO subscriptionRequestDTO, Member member) {

        Member findAdmin = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        if (!findAdmin.getRole().equals("ADMIN")) {
            throw new ApiException(NOT_MATCH_AUTHORIZTION);
        }

        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(
                () -> new ApiException(EMPTY_SUBSCRIPTION)
        );

        subscription.update(subscriptionRequestDTO);

        return ResponseEntity.ok(MessageDTO.builder()
                .message("가입 승인이 완료되었습니다")
                .statusCode(HttpStatus.OK.value())
                .build());
    }
}
