package com.example.vvs.domain.subscription.repository;

import com.example.vvs.domain.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByIsApprovalOrderByApplyDateDesc(String isApproval);

    List<Subscription> findAllByMemberIdOrderByApplyDateDesc(Long memberId);

    Optional<Subscription> findByProductIdAndMemberId(Long productId, Long memberId);
}
