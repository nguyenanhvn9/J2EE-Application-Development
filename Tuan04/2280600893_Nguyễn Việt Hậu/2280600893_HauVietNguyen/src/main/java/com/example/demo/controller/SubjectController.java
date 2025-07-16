package com.example.demo.controller;

import com.example.demo.model.Subject;
import com.example.demo.service.SubjectService;
import com.example.demo.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        return "subject/list";
    }

    @GetMapping("/create")
    public String createSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("faculties", facultyService.findAll());
        return "subject/add";
    }

    @PostMapping("/save")
    public String saveSubject(@ModelAttribute Subject subject) {
        subjectService.save(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/edit/{id}")
    public String editSubject(@PathVariable UUID id, Model model) {
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);
        model.addAttribute("faculties", facultyService.findAll());
        return "subject/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable UUID id) {
        subjectService.deleteById(id);
        return "redirect:/subjects";
    }
}
