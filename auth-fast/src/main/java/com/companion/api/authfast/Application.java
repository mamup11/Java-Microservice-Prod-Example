package com.companion.api.authfast;

import com.companion.api.authfast.registration.api.RegistrationApi;
import com.companion.api.authfast.registration.controller.dto.RegistrationDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//This is done to discover and use only the error handler. We do not want to autoconfigure the interceptors
@ComponentScan(basePackages = {"com.companion.api.authfast", "com.companion.api.commons.error"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
