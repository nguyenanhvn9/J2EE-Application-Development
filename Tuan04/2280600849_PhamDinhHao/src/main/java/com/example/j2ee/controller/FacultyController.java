package com.example.j2ee.controller;

import com.example.j2ee.model.Faculty;
import com.example.j2ee.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/faculties")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public String listFaculties(Model model) {
        model.addAttribute("faculties", facultyService.findAll());
        return "faculties";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "faculty_form";
    }

    @PostMapping("/add")
    public String addFaculty(@ModelAttribute Faculty faculty) {
        facultyService.save(faculty);
        return "redirect:/faculties";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Faculty faculty = facultyService.findById(id).orElseThrow();
        model.addAttribute("faculty", faculty);
        return "faculty_form";
    }

    @PostMapping("/edit/{id}")
    public String editFaculty(@PathVariable Long id, @ModelAttribute Faculty faculty) {
        faculty.setId(id);
        facultyService.save(faculty);
        return "redirect:/faculties";
    }

    @GetMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable Long id) {
        facultyService.deleteById(id);
        return "redirect:/faculties";
    }
} 