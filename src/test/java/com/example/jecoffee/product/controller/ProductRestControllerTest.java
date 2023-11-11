package com.example.jecoffee.product.controller;

import com.example.jecoffee.product.controller.dto.CreateProductRequest;
import com.example.jecoffee.product.entity.Category;
import com.example.jecoffee.product.entity.Product;
import com.example.jecoffee.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Test
    @DisplayName("상품 목록 조회 성공")
    void Success_ListProduct() throws Exception {
        // given
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            products.add(createProductByCategory(Category.COFFEE));
            products.add(createProductByCategory(Category.JUICE));
            products.add(createProductByCategory(Category.ADE));
        }
        when(productService.getAllProducts()).thenReturn(products);

        // when, then
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 목록을 성공적으로 조회하였습니다."));
    }

    @Test
    @DisplayName("상품 목록 조회 성공 - 카테고리 지정한 경우")
    void Success_ListProduct_WithCategory() throws Exception {
        // given
        List<Product> products = new ArrayList<>();
        Category category = Category.COFFEE;
        for (int i = 0; i < 3; i++) {
            products.add(createProductByCategory(Category.COFFEE));
        }
        when(productService.getProductsByCategory(category)).thenReturn(products);

        // when, then
        mockMvc.perform(get("/api/v1/products")
                        .param("category", category.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 목록을 성공적으로 조회하였습니다."));
    }

    @Test
    @DisplayName("상품 등록 성공")
    void Success_NewProduct() throws Exception {
        // given
        Product product = createProductByCategory(Category.COFFEE);
        CreateProductRequest productRequest = new CreateProductRequest(product.getProductName(), product.getCategory().getName(), product.getPrice(), product.getDescription());
        when(productService.createProduct(productRequest.getProductName(),
                Category.getValueByName(productRequest.getCategory()),
                productRequest.getPrice(),
                productRequest.getDescription())).thenReturn(product);

        // when, then
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품을 성공적으로 등록하였습니다."));
    }

    @Test
    @DisplayName("특정 이름이 포함된 상품 목록 조회 성공")
    void Success_SearchProduct() throws Exception {
        // given
        List<Product> products = new ArrayList<>();
        String name = "민트 초코";
        for (int i = 0; i < 3; i++) {
            products.add(createProductByNameAndCategory("민트 초코 커피", Category.COFFEE));
            products.add(createProductByNameAndCategory("민트 초코 주스", Category.JUICE));
            products.add(createProductByNameAndCategory("민트 초코 에이드", Category.ADE));
        }
        when(productService.searchProductsByName(name)).thenReturn(products);

        // when, then
        mockMvc.perform(get("/api/v1/products/name")
                        .param("name", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("특정 이름이 들어간 상품을 성공적으로 검색하였습니다."));
    }

    private Product createProductByCategory(Category category) {
        return new Product(UUID.randomUUID(), "아바라", category, 3000, "달아요", LocalDateTime.now(), LocalDateTime.now());
    }

    private Product createProductByNameAndCategory(String name, Category category) {
        return new Product(UUID.randomUUID(), name, category, 3000, "달아요", LocalDateTime.now(), LocalDateTime.now());
    }
}