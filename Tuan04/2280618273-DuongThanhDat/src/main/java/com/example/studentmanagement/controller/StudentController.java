package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    // Hiển thị danh sách sinh viên
    @GetMapping
    public String listStudents(Model model) {
        try {
            model.addAttribute("students", studentService.getAllStudents());
            return "students/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải danh sách sinh viên: " + e.getMessage());
            model.addAttribute("students", java.util.Collections.emptyList());
            return "students/list";
        }
    }
    
    // Hiển thị form thêm sinh viên mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }
    
    // Xử lý thêm sinh viên mới
    @PostMapping
    public String createStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        try {
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("success", "Thêm sinh viên thành công!");
            return "redirect:/students";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm sinh viên: " + e.getMessage());
            return "redirect:/students/new";
        }
    }
    
    // Hiển thị form sửa sinh viên
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID: " + id));
            model.addAttribute("student", student);
            return "students/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải thông tin sinh viên: " + e.getMessage());
            return "redirect:/students";
        }
    }
    
    // Xử lý cập nhật sinh viên
    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student, 
                               RedirectAttributes redirectAttributes) {
        try {
            student.setId(id);
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("success", "Cập nhật sinh viên thành công!");
            return "redirect:/students";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật sinh viên: " + e.getMessage());
            return "redirect:/students/" + id + "/edit";
        }
    }
    
    // Xóa sinh viên
    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("success", "Xóa sinh viên thành công!");
            return "redirect:/students";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa sinh viên: " + e.getMessage());
            return "redirect:/students";
        }
    }
}
