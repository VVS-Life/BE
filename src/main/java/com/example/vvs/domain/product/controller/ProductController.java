package com.example.vvs.domain.product.controller;

import com.example.vvs.domain.product.dto.ProductRequestDTO;
import com.example.vvs.domain.product.dto.ProductResponseDTO;
import com.example.vvs.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> productList(){
        return productService.findProductList();
    }

    @GetMapping("/products?category={category}")
    public ResponseEntity<List<ProductResponseDTO>> productList(@RequestParam String category){
        return productService.findProductListByCategory(category);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> productDetail(@RequestParam Long id){
        return productService.findProductDetailById(id);
    }

//    @PostMapping("/products/{id}/calc")
//    public ResponseEntity<ProductResponseDTO> productPriceCalc(@RequestBody char gender,
//                                                               @RequestBody String birth) {
//        return productService.calcExpectedPrice();
//    }
}