package com.example.j2ee.controller;

import com.example.j2ee.model.StudentClass;
import com.example.j2ee.model.Subject;
import com.example.j2ee.service.StudentClassService;
import com.example.j2ee.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/classes")
public class StudentClassController {
    @Autowired
    private StudentClassService studentClassService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String listClasses(Model model) {
        model.addAttribute("classes", studentClassService.findAll());
        return "classes";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("studentClass", new StudentClass());
        model.addAttribute("subjects", subjectService.findAll());
        return "class_form";
    }

    @PostMapping("/add")
    public String addClass(@ModelAttribute StudentClass studentClass) {
        studentClassService.save(studentClass);
        return "redirect:/classes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudentClass studentClass = studentClassService.findById(id).orElseThrow();
        model.addAttribute("studentClass", studentClass);
        model.addAttribute("subjects", subjectService.findAll());
        return "class_form";
    }

    @PostMapping("/edit/{id}")
    public String editClass(@PathVariable Long id, @ModelAttribute StudentClass studentClass) {
        studentClass.setId(id);
        studentClassService.save(studentClass);
        return "redirect:/classes";
    }

    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable Long id) {
        studentClassService.deleteById(id);
        return "redirect:/classes";
    }
} 