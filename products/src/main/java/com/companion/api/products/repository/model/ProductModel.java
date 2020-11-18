package com.companion.api.products.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private long value;
    private String imageUrl;
    private Featured featured;

    public enum Featured {
        FEATURED,
        NOT_FEATURED
    }
}
