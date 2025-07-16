package com.example.demo.controller;

import com.example.demo.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FacultyService facultyService;

    @GetMapping("/home")
    public String home() {
        return "redirect:/books";
    }

    @GetMapping("/school")
    public String schoolStructure(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "school";
    }
}