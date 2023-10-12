package com.example.vvs.domain.subscription.repository;

import com.example.vvs.domain.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByMemberId(Long memberId);

    @Query(value = "SELECT period FROM Subscription WHERE ID = :id", nativeQuery = true)
    int findByIdOfPeriod(Long id);

}
