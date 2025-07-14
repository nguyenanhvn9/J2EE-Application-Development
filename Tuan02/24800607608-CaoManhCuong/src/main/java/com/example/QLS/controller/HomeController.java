package com.example.QLS.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
public class HomeController {
    @RequestMapping
    public String index() {
        return "index";
    }
}
