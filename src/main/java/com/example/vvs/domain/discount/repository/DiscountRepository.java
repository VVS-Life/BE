package com.example.vvs.domain.discount.repository;

import com.example.vvs.domain.discount.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
