package com.companion.api.authfast;

import com.companion.api.authfast.registration.api.RegistrationApi;
import com.companion.api.authfast.registration.controller.dto.RegistrationDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.companion.api")
public class AuthFastApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthFastApplication.class, args);
    }

    @Bean
    public CommandLineRunner fillMocksInDb(RegistrationApi registrationApi) {
        return (args) -> {
            registrationApi.register(RegistrationDto.builder()
                    .username("admin")
                    .password("admin")
                    .build());
        };
    }
}
