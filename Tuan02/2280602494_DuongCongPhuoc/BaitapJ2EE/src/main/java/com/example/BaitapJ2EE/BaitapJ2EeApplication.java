package com.example.BaitapJ2EE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BaitapJ2EeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaitapJ2EeApplication.class, args);
	}

}
