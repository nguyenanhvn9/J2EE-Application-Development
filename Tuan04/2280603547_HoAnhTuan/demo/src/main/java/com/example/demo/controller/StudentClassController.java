package com.example.demo.controller;

import com.example.demo.model.StudentClass;
import com.example.demo.service.StudentClassService;
import com.example.demo.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student-classes")
@RequiredArgsConstructor
public class StudentClassController {
    private final StudentClassService studentClassService;
    private final SubjectService subjectService;

    @GetMapping
    public String listStudentClasses(Model model) {
        model.addAttribute("studentClasses", studentClassService.getAllStudentClasses());
        return "student_classes/student-classes-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("studentClass", new StudentClass());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "student_classes/add-student-class";
    }

    @PostMapping("/add")
    public String addStudentClass(@Valid @ModelAttribute("studentClass") StudentClass studentClass, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "student_classes/add-student-class";
        }
        studentClassService.saveStudentClass(studentClass);
        return "redirect:/student-classes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudentClass studentClass = studentClassService.getStudentClassById(id)
                .orElseThrow(() -> new IllegalStateException("StudentClass with ID " + id + " does not exist."));
        model.addAttribute("studentClass", studentClass);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "student_classes/edit-student-class";
    }

    @PostMapping("/edit/{id}")
    public String updateStudentClass(@PathVariable Long id, @Valid @ModelAttribute("studentClass") StudentClass studentClass, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "student_classes/edit-student-class";
        }
        studentClass.setId(id);
        studentClassService.saveStudentClass(studentClass);
        return "redirect:/student-classes";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudentClass(@PathVariable Long id) {
        studentClassService.deleteStudentClassById(id);
        return "redirect:/student-classes";
    }
}