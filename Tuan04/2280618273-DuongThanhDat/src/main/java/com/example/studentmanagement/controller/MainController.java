package com.example.studentmanagement.controller;

import com.example.studentmanagement.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    
    @Autowired
    private FacultyService facultyService;
    
    @GetMapping("/")
    public String index(Model model) {
        try {
            model.addAttribute("faculties", facultyService.getAllFaculties());
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải dữ liệu: " + e.getMessage());
            model.addAttribute("faculties", java.util.Collections.emptyList());
            return "index";
        }
    }
    
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "Application is running!");
        return "test";
    }
}
