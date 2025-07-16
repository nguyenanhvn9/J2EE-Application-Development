//package org.example.booking.controller;
//
//import org.example.booking.model.Subject;
//import org.example.booking.service.SubjectService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/subjects")
//public class SubjectController {
//    @Autowired
//    private SubjectService subjectService;
//
//    @GetMapping
//    public String listSubjects(Model model) {
//        model.addAttribute("subjects", subjectService.findAll());
//        return "subject/list";
//    }
//
//    @GetMapping("/new")
//    public String createSubjectForm(Model model) {
//        model.addAttribute("subject", new Subject());
//        return "subject/form";
//    }
//
//    @PostMapping
//    public String saveSubject(@ModelAttribute Subject subject) {
//        subjectService.save(subject);
//        return "redirect:/subjects";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editSubjectForm(@PathVariable Long id, Model model) {
//        Subject subject = subjectService.findById(id);
//        model.addAttribute("subject", subject);
//        return "subject/form";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteSubject(@PathVariable Long id) {
//        subjectService.delete(id);
//        return "redirect:/subjects";
//    }
//}
