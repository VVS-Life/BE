package com.example.vvs.domain.subscription.repository;

import com.example.vvs.domain.product.entity.Product;
import com.example.vvs.domain.subscription.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Page<Subscription> findAllByOrderByIdDesc(Pageable pageable);

    Page<Subscription> findAllByIdOrderByIdDesc(Long id, Pageable pageable);

    Optional<Subscription> findByProductId(Long productId);
}
