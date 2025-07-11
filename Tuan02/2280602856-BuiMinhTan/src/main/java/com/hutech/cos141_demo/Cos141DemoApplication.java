package com.hutech.cos141_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Cos141DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cos141DemoApplication.class, args);
	}

}
