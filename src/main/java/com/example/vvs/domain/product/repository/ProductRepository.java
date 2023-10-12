package com.example.vvs.domain.product.repository;

import com.example.vvs.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryOrderByIdDesc(String category);

}
