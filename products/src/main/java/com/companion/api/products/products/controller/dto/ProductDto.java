package com.companion.api.products.products.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Long value;
    private String imageUrl;
}
