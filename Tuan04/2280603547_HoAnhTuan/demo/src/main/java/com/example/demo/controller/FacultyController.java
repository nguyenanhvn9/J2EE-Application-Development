package com.example.demo.controller;

import com.example.demo.model.Faculty;
import com.example.demo.service.FacultyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/faculties")
@RequiredArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;

    @GetMapping
    public String listFaculties(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "faculties/faculties-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "faculties/add-faculty";
    }

    @PostMapping("/add")
    public String addFaculty(@Valid @ModelAttribute("faculty") Faculty faculty, BindingResult result) {
        if (result.hasErrors()) {
            return "faculties/add-faculty";
        }
        facultyService.saveFaculty(faculty);
        return "redirect:/faculties";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Faculty faculty = facultyService.getFacultyById(id)
                .orElseThrow(() -> new IllegalStateException("Faculty with ID " + id + " does not exist."));
        model.addAttribute("faculty", faculty);
        return "faculties/edit-faculty";
    }

    @PostMapping("/edit/{id}")
    public String updateFaculty(@PathVariable Long id, @Valid @ModelAttribute("faculty") Faculty faculty, BindingResult result) {
        if (result.hasErrors()) {
            return "faculties/edit-faculty";
        }
        faculty.setId(id);
        facultyService.saveFaculty(faculty);
        return "redirect:/faculties";
    }

    @PostMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFacultyById(id);
        return "redirect:/faculties";
    }
}