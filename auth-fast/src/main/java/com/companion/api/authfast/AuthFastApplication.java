package com.companion.api.authfast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.companion.api")
public class AuthFastApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthFastApplication.class, args);
    }

}
