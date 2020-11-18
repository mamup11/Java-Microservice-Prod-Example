package com.companion.api.products.repository;

import com.companion.api.products.repository.model.FavoriteProductsModel;
import com.companion.api.products.repository.model.ProductModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteProductsRepository extends CrudRepository<FavoriteProductsModel, Long> {

    List<FavoriteProductsModel> findAll();
    List<FavoriteProductsModel> findAllById_UserId(String userId);


}
