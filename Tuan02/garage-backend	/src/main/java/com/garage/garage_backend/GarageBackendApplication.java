package com.garage.garage_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GarageBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GarageBackendApplication.class, args);
	}

}
