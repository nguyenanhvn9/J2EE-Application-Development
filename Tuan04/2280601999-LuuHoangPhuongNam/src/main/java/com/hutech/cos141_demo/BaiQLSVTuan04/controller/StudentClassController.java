package com.hutech.cos141_demo.BaiQLSVTuan04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hutech.cos141_demo.BaiQLSVTuan04.model.StudentClass;
import com.hutech.cos141_demo.BaiQLSVTuan04.service.StudentClassService;
import com.hutech.cos141_demo.BaiQLSVTuan04.service.SubjectService;

@Controller
@RequestMapping("/classes")
public class StudentClassController {
    @Autowired
    private StudentClassService studentClassService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String listClasses(Model model) {
        model.addAttribute("classes", studentClassService.getAllStudentClasses());
        return "classes";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("studentClass", new StudentClass());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "add-class";
    }

    @PostMapping("/add")
    public String addClass(@ModelAttribute StudentClass studentClass) {
        studentClassService.saveStudentClass(studentClass);
        return "redirect:/classes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudentClass studentClass = studentClassService.getStudentClassById(id).orElseThrow();
        model.addAttribute("studentClass", studentClass);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "edit-class";
    }

    @PostMapping("/edit/{id}")
    public String editClass(@PathVariable Long id, @ModelAttribute StudentClass studentClass) {
        studentClass.setId(id);
        studentClassService.saveStudentClass(studentClass);
        return "redirect:/classes";
    }

    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable Long id) {
        studentClassService.deleteStudentClass(id);
        return "redirect:/classes";
    }
} 