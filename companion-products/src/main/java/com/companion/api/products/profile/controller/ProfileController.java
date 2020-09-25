package com.companion.api.products.profile.controller;

import com.companion.api.products.profile.controller.resources.ProductDto;
import com.companion.api.products.profile.model.ProductModel;
import com.companion.api.products.profile.model.ProductRepository;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class ProfileController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product")
    public ResponseEntity<?> saveProfile(@RequestBody ProductDto productDto) {
        ProductModel product = ProductModel.builder()
                .name(productDto.getName())
                .value(productDto.getValue())
                .build();

        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) {
        Preconditions.checkArgument(productId != null, "The id provided must not be null");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProfile() {
        log.info("This is a info message");
        return ResponseEntity.ok(productRepository.findAll());
    }
}
