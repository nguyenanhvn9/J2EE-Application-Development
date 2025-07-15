package com.hutech.book_user_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching  
public class BookUserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookUserManagementApplication.class, args);
    }
}
