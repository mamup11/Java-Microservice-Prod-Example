package com.companion.api.products.products;

import com.companion.api.products.products.controller.dto.ProductDto;
import com.companion.api.products.repository.model.ProductModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Converter<ProductModel, ProductDto> {

    @Override
    @NonNull
    public ProductDto convert(ProductModel productModel) {
        return ProductDto.builder()
                .id(productModel.getId())
                .name(productModel.getName())
                .description(productModel.getDescription())
                .value(productModel.getValue())
                .imageUrl(productModel.getImageUrl())
                .build();
    }
}
