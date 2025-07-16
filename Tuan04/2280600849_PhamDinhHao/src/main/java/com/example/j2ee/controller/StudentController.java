package com.example.j2ee.controller;

import com.example.j2ee.model.Student;
import com.example.j2ee.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_form";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id).orElseThrow();
        model.addAttribute("student", student);
        return "student_form";
    }

    @PostMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, @ModelAttribute Student student) {
        student.setId(id);
        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/students";
    }
} 