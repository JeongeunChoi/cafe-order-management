package com.example.jecoffee.product.controller;

import com.example.jecoffee.global.SuccessMessage;
import com.example.jecoffee.product.controller.dto.CreateProductRequest;
import com.example.jecoffee.product.entity.Category;
import com.example.jecoffee.product.entity.Product;
import com.example.jecoffee.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    private final ProductService productService;


    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<SuccessMessage> productList(@RequestParam Optional<Category> category) {
        List<Product> products = category.map(productService::getProductsByCategory).orElse(productService.getAllProducts());
        return ResponseEntity.ok(
                new SuccessMessage("상품 목록을 성공적으로 조회하였습니다."
                        , HttpStatus.OK, products));
    }

    @PostMapping("/products")
    public ResponseEntity<SuccessMessage> newProduct(@RequestBody CreateProductRequest createProductRequest) {
        Product product = productService.createProduct(createProductRequest.getProductName(),
                Category.getValueByName(createProductRequest.getCategory()),
                createProductRequest.getPrice(),
                createProductRequest.getDescription());

        return ResponseEntity.ok(
                new SuccessMessage("상품을 성공적으로 등록하였습니다."
                        , HttpStatus.CREATED, product));
    }

    @GetMapping("/products/name")
    public ResponseEntity<SuccessMessage> searchProduct(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(
                new SuccessMessage("특정 이름이 들어간 상품을 성공적으로 검색하였습니다."
                        , HttpStatus.CREATED, products));
    }
}
