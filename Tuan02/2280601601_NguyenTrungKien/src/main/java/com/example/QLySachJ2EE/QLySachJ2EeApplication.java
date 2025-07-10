package com.example.QLySachJ2EE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QLySachJ2EeApplication {

	public static void main(String[] args) {
		SpringApplication.run(QLySachJ2EeApplication.class, args);
	}

} 