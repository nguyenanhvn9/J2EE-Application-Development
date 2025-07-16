package com.example.demo.controller;

import com.example.demo.model.Subject;
import com.example.demo.service.FacultyService;
import com.example.demo.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final FacultyService facultyService;

    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjects/subjects-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "subjects/add-subject";
    }

    @PostMapping("/add")
    public String addSubject(@Valid @ModelAttribute("subject") Subject subject, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", facultyService.getAllFaculties());
            return "subjects/add-subject";
        }
        subjectService.saveSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Subject subject = subjectService.getSubjectById(id)
                .orElseThrow(() -> new IllegalStateException("Subject with ID " + id + " does not exist."));
        model.addAttribute("subject", subject);
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "subjects/edit-subject";
    }

    @PostMapping("/edit/{id}")
    public String updateSubject(@PathVariable Long id, @Valid @ModelAttribute("subject") Subject subject, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", facultyService.getAllFaculties());
            return "subjects/edit-subject";
        }
        subject.setId(id);
        subjectService.saveSubject(subject);
        return "redirect:/subjects";
    }

    @PostMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubjectById(id);
        return "redirect:/subjects";
    }
}