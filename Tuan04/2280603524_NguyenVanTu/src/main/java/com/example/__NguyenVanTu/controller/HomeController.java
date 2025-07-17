package com.example.__NguyenVanTu.controller;

import com.example.__NguyenVanTu.model.Faculty;
import com.example.__NguyenVanTu.model.Student;
import com.example.__NguyenVanTu.service.FacultyService;
import com.example.__NguyenVanTu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private FacultyService facultyService;
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping("/")
    public String home(Model model) {
        List<Faculty> faculties = facultyService.findAll();
        List<Student> allStudents = studentService.findAll();
        
        model.addAttribute("faculties", faculties);
        model.addAttribute("allStudents", allStudents);
        return "index";
    }
}
