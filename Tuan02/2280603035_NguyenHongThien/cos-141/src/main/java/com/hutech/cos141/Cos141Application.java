package com.hutech.cos141;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Cos141Application {
    public static void main(String[] args) {
        SpringApplication.run(Cos141Application.class, args);
    }
}
