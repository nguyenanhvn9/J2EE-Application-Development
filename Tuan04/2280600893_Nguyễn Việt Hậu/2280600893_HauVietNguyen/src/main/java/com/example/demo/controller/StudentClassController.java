package com.example.demo.controller;

import com.example.demo.model.StudentClass;
import com.example.demo.service.StudentClassService;
import com.example.demo.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/classes")
public class StudentClassController {

    @Autowired
    private StudentClassService classService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String listClasses(Model model) {
        model.addAttribute("classes", classService.findAll());
        return "class/list";
    }

    @GetMapping("/create")
    public String createClassForm(Model model) {
        model.addAttribute("studentClass", new StudentClass());
        model.addAttribute("subjects", subjectService.findAll());
        return "class/add";
    }

    @PostMapping("/save")
    public String saveClass(@ModelAttribute StudentClass studentClass) {
        if (studentClass.getId() != null) {
            StudentClass existing = classService.findById(studentClass.getId());
            studentClass.setStudents(existing.getStudents()); // giữ lại danh sách sinh viên
        }

        classService.save(studentClass);
        return "redirect:/classes";
    }

    @GetMapping("/edit/{id}")
    public String editClass(@PathVariable UUID id, Model model) {
        StudentClass clazz = classService.findById(id);
        model.addAttribute("studentClass", clazz);
        model.addAttribute("subjects", subjectService.findAll());
        return "class/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable UUID id) {
        classService.deleteById(id);
        return "redirect:/classes";
    }
}
