package com.companion.api.products.repository.jdbc;

import com.companion.api.products.repository.jdbc.row.mappers.ProductRowMapper;
import com.companion.api.products.repository.model.ProductModel;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class JdbcFavoriteProducts {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcFavoriteProducts(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FAVORITE_PRODUCTS_BY_USER_ID = "SELECT " +
            "p.* " +
            "FROM product p " +
            "inner join favorite_product fp " +
            "on fp.product_id = p.id " +
            "WHERE fp.user_id = :user_id";

    public List<ProductModel> findFavoriteProducts(String userId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(userId), "UserID must be provided to query");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);

        return jdbcTemplate.query(SELECT_FAVORITE_PRODUCTS_BY_USER_ID, params, 
                ProductRowMapper.PRODUCT_MODEL_ROW_MAPPER);
    }

}
