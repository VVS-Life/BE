package com.example.vvs.domain.product.controller;

import com.example.vvs.domain.product.dto.ProductDTO;
import com.example.vvs.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/productList")
    public List<ProductDTO> productList(@RequestParam String category){
        return productService.findProductListByCategory(category);
    }

}
