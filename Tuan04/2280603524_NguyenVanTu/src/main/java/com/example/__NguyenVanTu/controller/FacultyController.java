package com.example.__NguyenVanTu.controller;

import com.example.__NguyenVanTu.model.Faculty;
import com.example.__NguyenVanTu.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/faculties")
public class FacultyController {
    
    @Autowired
    private FacultyService facultyService;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("faculties", facultyService.findAll());
        return "faculties/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "faculties/form";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Faculty> faculty = facultyService.findById(id);
        if (faculty.isPresent()) {
            model.addAttribute("faculty", faculty.get());
            return "faculties/form";
        }
        return "redirect:/faculties";
    }
    
    @PostMapping
    public String save(@ModelAttribute Faculty faculty, RedirectAttributes redirectAttributes) {
        try {
            facultyService.save(faculty);
            redirectAttributes.addFlashAttribute("success", "Khoa đã được lưu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi lưu khoa!");
        }
        return "redirect:/faculties";
    }
    
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            facultyService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Khoa đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa khoa này!");
        }
        return "redirect:/faculties";
    }
}
