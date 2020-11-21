package com.companion.api.products;

import com.companion.api.products.repository.FavoriteProductsRepository;
import com.companion.api.products.repository.ProductRepository;
import com.companion.api.products.repository.model.FavoriteProductsModel;
import com.companion.api.products.repository.model.ProductModel;
import com.google.common.collect.Lists;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.companion.api")
public class ProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Bean
    public CommandLineRunner fillMocksInDb(ProductRepository productRepository,
                                           FavoriteProductsRepository favoriteProductsRepository) {
        return (args) -> {
            // save a few products
            productRepository.save(ProductModel.builder().name("Blue Tshirt")
                    .description("Amazing blue T-Shirt")
                    .value(15000L)
                    .featured(ProductModel.Featured.NOT_FEATURED)
                    .imageUrl("www.image.com")
                    .build());
            productRepository.save(ProductModel.builder().name("Red Tshirt")
                    .description("Amazing red T-Shirt")
                    .value(35000L)
                    .featured(ProductModel.Featured.NOT_FEATURED)
                    .imageUrl("www.image.com")
                    .build());
            productRepository.save(ProductModel.builder().name("Green Tshirt")
                    .description("Amazing green T-Shirt")
                    .value(20000L)
                    .featured(ProductModel.Featured.NOT_FEATURED)
                    .imageUrl("www.image.com")
                    .build());
            productRepository.save(ProductModel.builder().name("Gray Tshirt")
                    .description("Amazing blue T-Shirt")
                    .value(10000L)
                    .featured(ProductModel.Featured.NOT_FEATURED)
                    .imageUrl("www.image.com")
                    .build());

            List<FavoriteProductsModel> favoriteProducts = Lists.newArrayList(
                    FavoriteProductsModel.builder()
                            .id(FavoriteProductsModel.FavoriteProductsModelKey.builder()
                                    .productId(1L)
                                    .userId("USER1")
                                    .build())
                            .build(),
                    FavoriteProductsModel.builder()
                            .id(FavoriteProductsModel.FavoriteProductsModelKey.builder()
                                    .productId(3L)
                                    .userId("USER2")
                                    .build())
                            .build(),
                    FavoriteProductsModel.builder()
                            .id(FavoriteProductsModel.FavoriteProductsModelKey.builder()
                                    .productId(4L)
                                    .userId("USER2")
                                    .build())
                            .build(),
                    FavoriteProductsModel.builder()
                            .id(FavoriteProductsModel.FavoriteProductsModelKey.builder()
                                    .productId(1L)
                                    .userId("USER3")
                                    .build())
                            .build(),
                    FavoriteProductsModel.builder()
                            .id(FavoriteProductsModel.FavoriteProductsModelKey.builder()
                                    .productId(3L)
                                    .userId("USER3")
                                    .build())
                            .build(),
                    FavoriteProductsModel.builder()
                            .id(FavoriteProductsModel.FavoriteProductsModelKey.builder()
                                    .productId(4L)
                                    .userId("USER3")
                                    .build())
                            .build()
            );

            favoriteProductsRepository.saveAll(favoriteProducts);
        };
    }
}
