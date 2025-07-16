//package org.example.booking.controller;
//
//import org.example.booking.model.Faculty;
//import org.example.booking.service.FacultyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class SchoolController {
//
//    @Autowired
//    private FacultyService facultyService;
//
//    @GetMapping("/")
//    public String home(Model model) {
//        List<Faculty> faculties = facultyService.findAll();
//        model.addAttribute("faculties", faculties);
//        return "index"; // Trả về trang index.html
//    }
//}
