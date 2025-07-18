package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.StudentClass;
import com.example.studentmanagement.service.StudentClassService;
import com.example.studentmanagement.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/classes")
public class StudentClassController {
    
    @Autowired
    private StudentClassService studentClassService;
    
    @Autowired
    private SubjectService subjectService;
    
    // Hiển thị danh sách lớp học
    @GetMapping
    public String listClasses(Model model) {
        try {
            model.addAttribute("classes", studentClassService.getAllStudentClasses());
            return "classes/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải danh sách lớp học: " + e.getMessage());
            model.addAttribute("classes", java.util.Collections.emptyList());
            return "classes/list";
        }
    }
    
    // Hiển thị form thêm lớp học mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("studentClass", new StudentClass());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "classes/form";
    }
    
    // Xử lý thêm lớp học mới
    @PostMapping
    public String createClass(@ModelAttribute StudentClass studentClass, RedirectAttributes redirectAttributes) {
        try {
            studentClassService.saveStudentClass(studentClass);
            redirectAttributes.addFlashAttribute("success", "Thêm lớp học thành công!");
            return "redirect:/classes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm lớp học: " + e.getMessage());
            return "redirect:/classes/new";
        }
    }
    
    // Hiển thị form sửa lớp học
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            StudentClass studentClass = studentClassService.getStudentClassById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học với ID: " + id));
            model.addAttribute("studentClass", studentClass);
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "classes/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải thông tin lớp học: " + e.getMessage());
            return "redirect:/classes";
        }
    }
    
    // Xử lý cập nhật lớp học
    @PostMapping("/{id}")
    public String updateClass(@PathVariable Long id, @ModelAttribute StudentClass studentClass, 
                             RedirectAttributes redirectAttributes) {
        try {
            studentClass.setId(id);
            studentClassService.saveStudentClass(studentClass);
            redirectAttributes.addFlashAttribute("success", "Cập nhật lớp học thành công!");
            return "redirect:/classes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật lớp học: " + e.getMessage());
            return "redirect:/classes/" + id + "/edit";
        }
    }
    
    // Xóa lớp học
    @PostMapping("/{id}/delete")
    public String deleteClass(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentClassService.deleteStudentClass(id);
            redirectAttributes.addFlashAttribute("success", "Xóa lớp học thành công!");
            return "redirect:/classes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa lớp học: " + e.getMessage());
            return "redirect:/classes";
        }
    }
}
