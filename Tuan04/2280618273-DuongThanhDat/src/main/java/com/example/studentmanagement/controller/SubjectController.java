package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.model.Faculty;
import com.example.studentmanagement.service.SubjectService;
import com.example.studentmanagement.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private FacultyService facultyService;
    
    // Hiển thị danh sách môn học
    @GetMapping
    public String listSubjects(Model model) {
        try {
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "subjects/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải danh sách môn học: " + e.getMessage());
            model.addAttribute("subjects", java.util.Collections.emptyList());
            return "subjects/list";
        }
    }
    
    // Hiển thị form thêm môn học mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "subjects/form";
    }
    
    // Xử lý thêm môn học mới
    @PostMapping
    public String createSubject(@ModelAttribute Subject subject, RedirectAttributes redirectAttributes) {
        try {
            subjectService.saveSubject(subject);
            redirectAttributes.addFlashAttribute("success", "Thêm môn học thành công!");
            return "redirect:/subjects";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm môn học: " + e.getMessage());
            return "redirect:/subjects/new";
        }
    }
    
    // Hiển thị form sửa môn học
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Subject subject = subjectService.getSubjectById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học với ID: " + id));
            model.addAttribute("subject", subject);
            model.addAttribute("faculties", facultyService.getAllFaculties());
            return "subjects/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải thông tin môn học: " + e.getMessage());
            return "redirect:/subjects";
        }
    }
    
    // Xử lý cập nhật môn học
    @PostMapping("/{id}")
    public String updateSubject(@PathVariable Long id, @ModelAttribute Subject subject, 
                               RedirectAttributes redirectAttributes) {
        try {
            subject.setId(id);
            subjectService.saveSubject(subject);
            redirectAttributes.addFlashAttribute("success", "Cập nhật môn học thành công!");
            return "redirect:/subjects";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật môn học: " + e.getMessage());
            return "redirect:/subjects/" + id + "/edit";
        }
    }
    
    // Xóa môn học
    @PostMapping("/{id}/delete")
    public String deleteSubject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            subjectService.deleteSubject(id);
            redirectAttributes.addFlashAttribute("success", "Xóa môn học thành công!");
            return "redirect:/subjects";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa môn học: " + e.getMessage());
            return "redirect:/subjects";
        }
    }
}
