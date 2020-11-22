package com.companion.api.products.repository.jdbc.row.mappers;

import com.companion.api.products.repository.model.ProductModel;
import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper {

    public static final RowMapper<ProductModel> PRODUCT_MODEL_ROW_MAPPER = (rs, rowNum) ->
            ProductModel.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .value(rs.getLong("value"))
                    .imageUrl(rs.getString("image_url"))
                    .featured(ProductModel.Featured.valueOf(rs.getString("featured")))
                    .build();
}
