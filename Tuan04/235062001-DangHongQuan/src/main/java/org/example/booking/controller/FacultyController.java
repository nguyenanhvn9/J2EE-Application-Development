//package org.example.booking.controller;
//
//import org.example.booking.model.Faculty;
//import org.example.booking.service.FacultyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/faculties")
//public class FacultyController {
//    @Autowired
//    private FacultyService facultyService;
//
//    @GetMapping
//    public String listFaculties(Model model) {
//        model.addAttribute("faculties", facultyService.findAll());
//        return "faculty/list";
//    }
//
//    @GetMapping("/new")
//    public String createFacultyForm(Model model) {
//        model.addAttribute("faculty", new Faculty());
//        return "faculty/form";
//    }
//
//    @PostMapping
//    public String saveFaculty(@ModelAttribute Faculty faculty) {
//        facultyService.save(faculty);
//        return "redirect:/faculties";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editFacultyForm(@PathVariable Long id, Model model) {
//        Faculty faculty = facultyService.findById(id);
//        model.addAttribute("faculty", faculty);
//        return "faculty/form";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteFaculty(@PathVariable Long id) {
//        facultyService.delete(id);
//        return "redirect:/faculties";
//    }
//}
