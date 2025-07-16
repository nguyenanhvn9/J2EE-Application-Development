//package org.example.booking.controller;
//
//import org.example.booking.model.StudentClass;
//import org.example.booking.service.StudentClassService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/student-classes")
//public class StudentClassController {
//    @Autowired
//    private StudentClassService studentClassService;
//
//    @GetMapping
//    public String listStudentClasses(Model model) {
//        model.addAttribute("studentClasses", studentClassService.findAll());
//        return "studentClass/list";
//    }
//
//    @GetMapping("/new")
//    public String createStudentClassForm(Model model) {
//        model.addAttribute("studentClass", new StudentClass());
//        return "studentClass/form";
//    }
//
//    @PostMapping
//    public String saveStudentClass(@ModelAttribute StudentClass studentClass) {
//        studentClassService.save(studentClass);
//        return "redirect:/student-classes";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editStudentClassForm(@PathVariable Long id, Model model) {
//        StudentClass studentClass = studentClassService.findById(id);
//        model.addAttribute("studentClass", studentClass);
//        return "studentClass/form";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteStudentClass(@PathVariable Long id) {
//        studentClassService.delete(id);
//        return "redirect:/student-classes";
//    }
//}
