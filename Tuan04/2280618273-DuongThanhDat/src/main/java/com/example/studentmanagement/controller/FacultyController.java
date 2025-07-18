package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Faculty;
import com.example.studentmanagement.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/faculties")
public class FacultyController {
    
    @Autowired
    private FacultyService facultyService;
    
    // Hiển thị danh sách khoa
    @GetMapping
    public String listFaculties(Model model) {
        try {
            model.addAttribute("faculties", facultyService.getAllFaculties());
            return "faculties/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải danh sách khoa: " + e.getMessage());
            model.addAttribute("faculties", java.util.Collections.emptyList());
            return "faculties/list";
        }
    }
    
    // Hiển thị form thêm khoa mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "faculties/form";
    }
    
    // Xử lý thêm khoa mới
    @PostMapping
    public String createFaculty(@ModelAttribute Faculty faculty, RedirectAttributes redirectAttributes) {
        try {
            facultyService.saveFaculty(faculty);
            redirectAttributes.addFlashAttribute("success", "Thêm khoa thành công!");
            return "redirect:/faculties";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm khoa: " + e.getMessage());
            return "redirect:/faculties/new";
        }
    }
    
    // Hiển thị form sửa khoa
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Faculty faculty = facultyService.getFacultyById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa với ID: " + id));
            model.addAttribute("faculty", faculty);
            return "faculties/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải thông tin khoa: " + e.getMessage());
            return "redirect:/faculties";
        }
    }
    
    // Xử lý cập nhật khoa
    @PostMapping("/{id}")
    public String updateFaculty(@PathVariable Long id, @ModelAttribute Faculty faculty, 
                               RedirectAttributes redirectAttributes) {
        try {
            faculty.setId(id);
            facultyService.saveFaculty(faculty);
            redirectAttributes.addFlashAttribute("success", "Cập nhật khoa thành công!");
            return "redirect:/faculties";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật khoa: " + e.getMessage());
            return "redirect:/faculties/" + id + "/edit";
        }
    }
    
    // Xóa khoa
    @PostMapping("/{id}/delete")
    public String deleteFaculty(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            facultyService.deleteFaculty(id);
            redirectAttributes.addFlashAttribute("success", "Xóa khoa thành công!");
            return "redirect:/faculties";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa khoa: " + e.getMessage());
            return "redirect:/faculties";
        }
    }
}
