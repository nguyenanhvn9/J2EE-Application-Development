//package org.example.booking.controller;
//
//import org.example.booking.model.Student;
//import org.example.booking.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/students")
//public class StudentController {
//    @Autowired
//    private StudentService studentService;
//
//    @GetMapping
//    public String listStudents(Model model) {
//        model.addAttribute("students", studentService.findAll());
//        return "student/list";
//    }
//
//    @GetMapping("/new")
//    public String createStudentForm(Model model) {
//        model.addAttribute("student", new Student());
//        return "student/form";
//    }
//
//    @PostMapping
//    public String saveStudent(@ModelAttribute Student student) {
//        studentService.save(student);
//        return "redirect:/students";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editStudentForm(@PathVariable Long id, Model model) {
//        Student student = studentService.findById(id);
//        model.addAttribute("student", student);
//        return "student/form";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteStudent(@PathVariable Long id) {
//        studentService.delete(id);
//        return "redirect:/students";
//    }
//}
