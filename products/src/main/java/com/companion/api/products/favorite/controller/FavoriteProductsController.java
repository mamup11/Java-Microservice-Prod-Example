package com.companion.api.products.favorite.controller;

import com.companion.api.products.favorite.api.FavoriteProductsApi;
import com.companion.api.products.products.ProductConverter;
import com.companion.api.products.products.api.ProductsApi;
import com.companion.api.products.products.controller.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/favorite")
public class FavoriteProductsController {

    private final FavoriteProductsApi favoriteProductsApi;
    private final ProductConverter productConverter;

    @Autowired
    public FavoriteProductsController(FavoriteProductsApi favoriteProductsApi,
                                      ProductConverter productConverter) {
        this.favoriteProductsApi = favoriteProductsApi;
        this.productConverter = productConverter;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getFavoriteProducts() {
        String clientId = "USER3";

        List<ProductDto> favoriteProducts = favoriteProductsApi.getFavoriteProducts(clientId).stream()
                .map(productConverter::convert)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteProducts);
    }
}
