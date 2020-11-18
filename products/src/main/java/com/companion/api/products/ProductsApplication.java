package com.companion.api.products;

import com.companion.api.products.repository.ProductRepository;
import com.companion.api.products.repository.model.ProductModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.companion.api")
public class ProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Bean
    public CommandLineRunner fillMocksInDb(ProductRepository repository) {
        return (args) -> {
            // save a few products
            repository.save(ProductModel.builder().name("Blue Tshirt")
                    .description("Amazing blue T-Shirt")
                    .value(15000L)
                    .featured(ProductModel.Featured.NOT_FEATURED)
                    .imageUrl("www.image.com")
                    .build());

            repository.save(ProductModel.builder().name("Red Tshirt")
                    .description("Amazing red T-Shirt")
                    .value(35000L)
                    .featured(ProductModel.Featured.NOT_FEATURED)
                    .imageUrl("www.image.com")
                    .build());

            repository.save(ProductModel.builder().name("Green Tshirt")
                    .description("Amazing green T-Shirt")
                    .value(20000L)
                    .featured(ProductModel.Featured.NOT_FEATURED)
                    .imageUrl("www.image.com")
                    .build());

            repository.save(ProductModel.builder().name("Gray Tshirt")
                    .description("Amazing blue T-Shirt")
                    .value(10000L)
                    .featured(ProductModel.Featured.NOT_FEATURED)
                    .imageUrl("www.image.com")
                    .build());
        };
    }
}
