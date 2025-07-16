package com.hutech.cos141_demo.BaiQLSVTuan04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hutech.cos141_demo.BaiQLSVTuan04.model.Faculty;
import com.hutech.cos141_demo.BaiQLSVTuan04.service.FacultyService;

@Controller
@RequestMapping("/faculties")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public String listFaculties(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "faculties";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "add-faculty";
    }

    @PostMapping("/add")
    public String addFaculty(@ModelAttribute Faculty faculty) {
        facultyService.saveFaculty(faculty);
        return "redirect:/faculties";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Faculty faculty = facultyService.getFacultyById(id).orElseThrow();
        model.addAttribute("faculty", faculty);
        return "edit-faculty";
    }

    @PostMapping("/edit/{id}")
    public String editFaculty(@PathVariable Long id, @ModelAttribute Faculty faculty) {
        faculty.setId(id);
        facultyService.saveFaculty(faculty);
        return "redirect:/faculties";
    }

    @GetMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return "redirect:/faculties";
    }
} 