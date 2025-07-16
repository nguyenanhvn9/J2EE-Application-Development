package com.hutech.cos141_demo.BaiTH124.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    @GetMapping("/home")
    @ResponseBody
    public String home() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head><meta charset='UTF-8'><title>Home</title></head>" +
                "<body>" +
                "<h1>Chào mừng bạn đến với Thymeleaf!</h1>" +
                "<p>Tên của bạn là: Nguyễn Huy Cường</p>" +
                "</body></html>";
    }
} 