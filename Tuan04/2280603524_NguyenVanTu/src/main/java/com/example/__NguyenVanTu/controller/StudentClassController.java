package com.example.__NguyenVanTu.controller;

import com.example.__NguyenVanTu.model.StudentClass;
import com.example.__NguyenVanTu.service.StudentClassService;
import com.example.__NguyenVanTu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/classes")
public class StudentClassController {
    
    @Autowired
    private StudentClassService studentClassService;
    
    @Autowired
    private SubjectService subjectService;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("classes", studentClassService.findAll());
        return "classes/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("studentClass", new StudentClass());
        model.addAttribute("subjects", subjectService.findAll());
        return "classes/form";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<StudentClass> studentClass = studentClassService.findById(id);
        if (studentClass.isPresent()) {
            model.addAttribute("studentClass", studentClass.get());
            model.addAttribute("subjects", subjectService.findAll());
            return "classes/form";
        }
        return "redirect:/classes";
    }
    
    @PostMapping
    public String save(@ModelAttribute StudentClass studentClass, RedirectAttributes redirectAttributes) {
        try {
            studentClassService.save(studentClass);
            redirectAttributes.addFlashAttribute("success", "Lớp học đã được lưu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi lưu lớp học!");
        }
        return "redirect:/classes";
    }
    
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentClassService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Lớp học đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa lớp học này!");
        }
        return "redirect:/classes";
    }
}
