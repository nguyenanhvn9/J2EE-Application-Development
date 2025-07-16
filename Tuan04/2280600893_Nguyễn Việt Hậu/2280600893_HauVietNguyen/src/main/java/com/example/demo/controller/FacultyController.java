package com.example.demo.controller;

import com.example.demo.model.Faculty;
import com.example.demo.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public String listFaculties(Model model) {
        model.addAttribute("faculties", facultyService.findAll());
        return "faculty/list";
    }

    @GetMapping("/create")
    public String createFacultyForm(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "faculty/add";
    }

    @PostMapping("/save")
    public String saveFaculty(@ModelAttribute Faculty faculty) {
        facultyService.save(faculty);
        return "redirect:/faculties";
    }

    @GetMapping("/edit/{id}")
    public String editFaculty(@PathVariable UUID id, Model model) {
        Faculty faculty = facultyService.findById(id);
        model.addAttribute("faculty", faculty);
        return "faculty/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable UUID id) {
        facultyService.deleteById(id);
        return "redirect:/faculties";
    }
}
