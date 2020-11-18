package com.companion.api.products.repository;

import com.companion.api.products.repository.model.ProductModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductModel, Long> {

    List<ProductModel> findAll();
    List<ProductModel> findAllByFeaturedEquals(ProductModel.Featured featured);

}
