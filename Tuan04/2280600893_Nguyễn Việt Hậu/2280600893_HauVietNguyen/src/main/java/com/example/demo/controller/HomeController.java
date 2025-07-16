package com.example.demo.controller;

import com.example.demo.model.Faculty;
import com.example.demo.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/")
    public String home(Model model) {
        List<Faculty> faculties = facultyService.findAll();
        model.addAttribute("faculties", faculties);
        model.addAttribute("pageTitle", "Trang chủ"); // dùng cho layout title
        return "home/index"; // chính xác đường dẫn file index.html trong thư mục templates/home/
    }
}
