package com.companion.api.products.products.api;

import com.companion.api.products.products.controller.dto.ProductDto;
import com.companion.api.products.repository.ProductRepository;
import com.companion.api.products.repository.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductsApi {

    private final ProductRepository productRepository;

    @Autowired
    public ProductsApi(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductModel createProduct(ProductDto productDto) {
        ProductModel model = ProductModel.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .value(productDto.getValue())
                .imageUrl(productDto.getImageUrl())
                .build();

        return productRepository.save(model);
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductModel getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<ProductModel> getFeaturedProducts() {
        return productRepository.findAllByFeaturedEquals(ProductModel.Featured.FEATURED);
    }

    public void markFeaturedProduct(Long id) {
        Optional<ProductModel> modelOpt = productRepository.findById(id);

        modelOpt.ifPresent(productModel -> {
            productModel.setFeatured(ProductModel.Featured.FEATURED);
            productRepository.save(productModel);
        });
    }
}
