package com.__TranThanhDat.__TranThanhDat.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
      @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }
}
