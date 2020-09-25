package com.companion.api.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.companion.api")
public class CompanionProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanionProductsApplication.class, args);
    }

}
