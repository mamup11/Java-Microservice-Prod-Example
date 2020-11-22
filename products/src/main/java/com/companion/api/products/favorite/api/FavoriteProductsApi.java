package com.companion.api.products.favorite.api;

import com.companion.api.products.repository.FavoriteProductsRepository;
import com.companion.api.products.repository.ProductRepository;
import com.companion.api.products.repository.jdbc.JdbcFavoriteProducts;
import com.companion.api.products.repository.model.ProductModel;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavoriteProductsApi {

    private final FavoriteProductsRepository favoriteProductsRepository;
    private final JdbcFavoriteProducts jdbcFavoriteProducts;
    private final ProductRepository productRepository;

    @Autowired
    public FavoriteProductsApi(FavoriteProductsRepository favoriteProductsRepository,
                               JdbcFavoriteProducts jdbcFavoriteProducts,
                               ProductRepository productRepository) {
        this.favoriteProductsRepository = favoriteProductsRepository;
        this.jdbcFavoriteProducts = jdbcFavoriteProducts;
        this.productRepository = productRepository;
    }
    
    public List<ProductModel> getFavoriteProducts(String userId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(userId), "UserId must be provided");
        return jdbcFavoriteProducts.findFavoriteProducts(userId);
    }
}
