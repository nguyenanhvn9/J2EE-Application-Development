package org.example.booking.controller;

import org.example.booking.model.StudentCrud;
import org.example.booking.service.StudentCrudService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentCrudController {

    private final StudentCrudService studentService;

    public StudentCrudController(StudentCrudService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentService.getAll());
        model.addAttribute("newStudent", new StudentCrud());
        return "student-crud";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("newStudent") StudentCrud student) {
        studentService.create(student);
        return "redirect:/students";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute StudentCrud student) {
        studentService.update(student.getId(), student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/students";
    }
}
