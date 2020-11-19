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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
public class ProductsController {

    private final ProductsApi productsApi;
    private final ProductConverter productConverter;

    @Autowired
    public ProductsController(ProductsApi productsApi, ProductConverter productConverter) {
        this.productsApi = productsApi;
        this.productConverter = productConverter;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productDtoList = productsApi.getAllProducts()
                .stream()
                .map(productConverter::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDtoList);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Preconditions.checkArgument(productDto != null, " Product information must be provided");

        ProductDto newProductDto = productConverter.convert(productsApi.createProduct(productDto));
        return ResponseEntity.ok(newProductDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        Preconditions.checkArgument(productId != null, "The product id must not be null");

        ProductDto newProductDto = productConverter.convert(productsApi.getById(productId));
        return ResponseEntity.ok(newProductDto);
    }
}
