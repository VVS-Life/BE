package com.example.vvs.domain.product.repository;

import com.example.vvs.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdDesc();

    List<Product> findAllByCategoryOrderByIdDesc(String category);

    @Query(value = "select price from Product  where id= :id")
    Integer findPriceById(Long id);
}
