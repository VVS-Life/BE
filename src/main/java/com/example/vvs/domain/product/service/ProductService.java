package com.example.vvs.domain.product.service;

import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.product.dto.PriceCalcResponseDTO;
import com.example.vvs.domain.product.dto.ProductRequestDTO;
import com.example.vvs.domain.product.dto.ProductResponseDTO;
import com.example.vvs.domain.product.dto.PriceCalcRequestDTO;
import com.example.vvs.domain.product.entity.Product;
import com.example.vvs.domain.product.repository.ProductRepository;
import com.example.vvs.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.vvs.exception.ErrorHandling.*;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<List<ProductResponseDTO>> findProductList() {
        List<Product> productList = productRepository.findAllByOrderByIdDesc();
        List<ProductResponseDTO> dtoList = new ArrayList<>();

        if (productList.isEmpty()) {
            throw  new ApiException(IS_EMPTY_LIST);
        }

        for (Product eachProduct : productList) {
            Product product = Product.builder()
                    .id(eachProduct.getId())
                    .productName(eachProduct.getProductName())
                    .content(eachProduct.getContent())
                    .guarantee(eachProduct.getGuarantee())
                    .price(eachProduct.getPrice())
                    .category(eachProduct.getCategory())
                    .build();

            ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().product(product).build();

            dtoList.add(productResponseDTO);
        }

        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<List<ProductResponseDTO>> findProductListByCategory(String category) {

        List<Product> productList = productRepository.findAllByCategoryOrderByIdDesc(category);
        List<ProductResponseDTO> dtoList = new ArrayList<>();

        if (productList.isEmpty()) {
            throw new ApiException(IS_EMPTY_LIST);
        }

        for (Product eachProduct : productList) {
            Product product = Product.builder()
                    .id(eachProduct.getId())
                    .productName(eachProduct.getProductName())
                    .content(eachProduct.getContent())
                    .price(eachProduct.getPrice())
                    .category(eachProduct.getCategory())
                    .build();

            ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().product(product).build();

            dtoList.add(productResponseDTO);
        }

        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<ProductResponseDTO> findProductDetailById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_PRODUCT)
        );

        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().product(product).build();

        return ResponseEntity.ok(productResponseDTO);
    }

    @Transactional
    public ResponseEntity<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO){

        Product product = Product.builder()
                .productName(productRequestDTO.getProductName())
                .content(productRequestDTO.getContent())
                .price(productRequestDTO.getPrice())
                .category(productRequestDTO.getCategory())
                .build();

        productRepository.save(product);

        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().product(product).build();

        return ResponseEntity.ok(productResponseDTO);
    }

    @Transactional
    public ResponseEntity<ProductResponseDTO> updateProduct(Long id, ProductRequestDTO productRequestDTO){

        Product product = productRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_PRODUCT)
        );

        product.update(productRequestDTO);

        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().product(product).build();

        return ResponseEntity.ok(productResponseDTO);
    }

    @Transactional
    public ResponseEntity<MessageDTO> deleteProduct(Long id){

        productRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_PRODUCT)
        );

        productRepository.deleteById(id);

        return ResponseEntity.ok(MessageDTO.builder()
                .message("상품 삭제 완료")
                .statusCode(OK.value())
                .build());
    }

    public ResponseEntity<PriceCalcResponseDTO> findCalcExpectedPrice(Long id, char gender, String birth) {

        Integer price = productRepository.findPriceById(id);

        if (price == 0) {
            throw new ApiException(NOT_FOUND_PRODUCT);
        }

        price = calcExpectedPrice(gender, birth, price);

        PriceCalcResponseDTO priceCalcResponseDTO = PriceCalcResponseDTO.builder()
                .price(price)
                .build();

        return ResponseEntity.ok(priceCalcResponseDTO);
    }

    public Integer calcExpectedPrice(char gender, String birth, Integer price) {

        PriceCalcRequestDTO priceCalcRequestDTO = PriceCalcRequestDTO.builder()
                .birth(birth)
                .gender(gender)
                .build();

        Double womanDiscount = 0.0;
        if(priceCalcRequestDTO.getGender()=='W')
            womanDiscount = 0.1;

        //19910101
        Integer age = Integer.valueOf(priceCalcRequestDTO.getBirth().substring(0,4));

        Integer totalPrice = price - (int)(price * womanDiscount + (2023-age)/10*price*0.18);

        return totalPrice;
    }
}