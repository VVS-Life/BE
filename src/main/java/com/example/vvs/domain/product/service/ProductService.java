package com.example.vvs.domain.product.service;

import com.example.vvs.domain.product.dto.ProductDTO;
import com.example.vvs.domain.product.entity.Product;
import com.example.vvs.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> findProductListByCategory(String category) {

        List<Product> productList = productRepository.findByCategory(category);
        List<ProductDTO> dtoList = new ArrayList<>();

//        if (productList.isEmpty()) {
//            throw new
//        }

        for (Product eachProduct : productList) {
            dtoList.add(ProductDTO.builder()
                    .productName(eachProduct.getProductName())
                    .content(eachProduct.getContent())
                    .price(eachProduct.getPrice())
                    .category(eachProduct.getCategory())
                    .build());
        }

        return dtoList;
    }
}
