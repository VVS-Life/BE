package com.example.vvs.domain.product.service;

import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.product.dto.ProductRequestDTO;
import com.example.vvs.domain.product.dto.ProductResponseDTO;
import com.example.vvs.domain.product.dto.UserInsuranceInfoDTO;
import com.example.vvs.domain.product.entity.Product;
import com.example.vvs.domain.product.repository.ProductRepository;
import com.example.vvs.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.vvs.exception.ErrorHandling.*;

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

    public ResponseEntity<List<ProductResponseDTO>> findProductListByCategory(String category) {

        List<Product> productList = productRepository.findAllByCategoryOrderByIdDesc(category);
        List<ProductResponseDTO> dtoList = new ArrayList<>();

        if (productList.isEmpty()) {
            throw new ApiException(IS_EMPTY_LIST);
        }

        for (Product eachProduct : productList) {
            Product product = Product.builder()
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
                () -> new ApiException(IS_NULL)
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
    public ResponseEntity<ProductResponseDTO> modifyProduct(Long id, ProductRequestDTO productRequestDTO){

        Product product = productRepository.findById(id).orElseThrow(
                () -> new ApiException(IS_NULL)
        );

        product.update(productRequestDTO);

        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().product(product).build();

        return ResponseEntity.ok(productResponseDTO);
    }

    @Transactional
    public ResponseEntity<MessageDTO> removeProduct(Long id){

        productRepository.findById(id).orElseThrow(
                () -> new ApiException(IS_NULL)
        );

        productRepository.deleteById(id);

        return ResponseEntity.ok(new MessageDTO("상품 삭제 완료", HttpStatus.OK));
    }

//    public ResponseEntity<Integer> calcExceptedPrice(Long id) {
//
//        Product product = productRepository.findById(id).orElseThrow(
//                () -> new ApiException(IS_NULL)
//        );
//
//        return ResponseEntity.ok();
//    }

//    public int calcExcepPrice(UserInsuranceInfoDTO userInsuranceInfoDTO) {
//
//        int age = userInsuranceInfoDTO.
//
//        int price = price - price * 0.1 + int((2023-age)/10)*price*0.18;
//
//        return price;
//    }
}