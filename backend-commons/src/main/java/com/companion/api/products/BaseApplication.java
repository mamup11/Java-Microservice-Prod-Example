package com.companion.api.products;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URI;

@SpringBootApplication
public class BaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			System.out.println(URI.create("1010"));
		};
	}

}
