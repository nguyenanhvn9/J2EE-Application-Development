package com.example.j2ee.controller;

import com.example.j2ee.model.Subject;
import com.example.j2ee.model.Faculty;
import com.example.j2ee.service.SubjectService;
import com.example.j2ee.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("faculties", facultyService.findAll());
        return "subject_form";
    }

    @PostMapping("/add")
    public String addSubject(@ModelAttribute Subject subject) {
        subjectService.save(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Subject subject = subjectService.findById(id).orElseThrow();
        model.addAttribute("subject", subject);
        model.addAttribute("faculties", facultyService.findAll());
        return "subject_form";
    }

    @PostMapping("/edit/{id}")
    public String editSubject(@PathVariable Long id, @ModelAttribute Subject subject) {
        subject.setId(id);
        subjectService.save(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteById(id);
        return "redirect:/subjects";
    }
} 