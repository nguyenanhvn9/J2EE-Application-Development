package com.example.__NguyenVanTu.controller;

import com.example.__NguyenVanTu.model.Subject;
import com.example.__NguyenVanTu.service.FacultyService;
import com.example.__NguyenVanTu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private FacultyService facultyService;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("faculties", facultyService.findAll());
        return "subjects/form";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Subject> subject = subjectService.findById(id);
        if (subject.isPresent()) {
            model.addAttribute("subject", subject.get());
            model.addAttribute("faculties", facultyService.findAll());
            return "subjects/form";
        }
        return "redirect:/subjects";
    }
    
    @PostMapping
    public String save(@ModelAttribute Subject subject, RedirectAttributes redirectAttributes) {
        try {
            subjectService.save(subject);
            redirectAttributes.addFlashAttribute("success", "Môn học đã được lưu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi lưu môn học!");
        }
        return "redirect:/subjects";
    }
    
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            subjectService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Môn học đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa môn học này!");
        }
        return "redirect:/subjects";
    }
}
