package com.hutech.cos141_demo.BaiQLSVTuan04.controller;

import com.hutech.cos141_demo.BaiQLSVTuan04.model.Subject;
import com.hutech.cos141_demo.BaiQLSVTuan04.model.Faculty;
import com.hutech.cos141_demo.BaiQLSVTuan04.service.SubjectService;
import com.hutech.cos141_demo.BaiQLSVTuan04.service.FacultyService;
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
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjects";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "add-subject";
    }

    @PostMapping("/add")
    public String addSubject(@ModelAttribute Subject subject) {
        subjectService.saveSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Subject subject = subjectService.getSubjectById(id).orElseThrow();
        model.addAttribute("subject", subject);
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "edit-subject";
    }

    @PostMapping("/edit/{id}")
    public String editSubject(@PathVariable Long id, @ModelAttribute Subject subject) {
        subject.setId(id);
        subjectService.saveSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }
} 