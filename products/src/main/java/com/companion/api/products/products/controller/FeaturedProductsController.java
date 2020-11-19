package com.companion.api.products.products.controller;

import com.companion.api.products.products.ProductConverter;
import com.companion.api.products.products.api.ProductsApi;
import com.companion.api.products.products.controller.dto.ProductDto;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/featured")
public class FeaturedProductsController {

    private final ProductsApi productsApi;
    private final ProductConverter productConverter;

    @Autowired
    public FeaturedProductsController(ProductsApi productsApi, ProductConverter productConverter) {
        this.productsApi = productsApi;
        this.productConverter = productConverter;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getFeaturedProducts() {
        List<ProductDto> productDtos = productsApi.getFeaturedProducts()
                .stream()
                .map(productConverter::convert)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    @PostMapping("/mark/{id}")
    public ResponseEntity<Void> markFeaturedProduct(@PathVariable("id") Long productId) {
        Preconditions.checkArgument(productId != null, " Product id must not be null");

        productsApi.markFeaturedProduct(productId);
        return ResponseEntity.ok().build();
    }
}
