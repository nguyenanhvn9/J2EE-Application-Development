package com.example.__NguyenVanTu.controller;

import com.example.__NguyenVanTu.model.Student;
import com.example.__NguyenVanTu.model.StudentClass;
import com.example.__NguyenVanTu.service.EnrollmentService;
import com.example.__NguyenVanTu.service.StudentClassService;
import com.example.__NguyenVanTu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @Autowired
    private StudentClassService studentClassService;
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("classes", studentClassService.findAll());
        return "enrollments/list";
    }
    
    @GetMapping("/class/{classId}")
    public String manageClassEnrollments(@PathVariable Long classId, Model model) {
        Optional<StudentClass> studentClass = studentClassService.findById(classId);
        if (studentClass.isPresent()) {
            List<Student> enrolledStudents = studentService.findStudentsEnrolledInClass(classId);
            List<Student> availableStudents = studentService.findStudentsNotEnrolledInClass(classId);
            
            model.addAttribute("studentClass", studentClass.get());
            model.addAttribute("enrolledStudents", enrolledStudents);
            model.addAttribute("availableStudents", availableStudents);
            return "enrollments/manage";
        }
        return "redirect:/enrollments";
    }
    
    @PostMapping("/enroll")
    public String enrollStudent(@RequestParam Long studentId, @RequestParam Long classId, 
                               RedirectAttributes redirectAttributes) {
        try {
            boolean success = enrollmentService.enrollStudent(studentId, classId);
            if (success) {
                redirectAttributes.addFlashAttribute("success", "Sinh viên đã được đăng ký vào lớp thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Sinh viên đã được đăng ký vào lớp này rồi!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi đăng ký!");
        }
        return "redirect:/enrollments/class/" + classId;
    }
    
    @PostMapping("/unenroll")
    public String unenrollStudent(@RequestParam Long studentId, @RequestParam Long classId, 
                                 RedirectAttributes redirectAttributes) {
        try {
            boolean success = enrollmentService.unenrollStudent(studentId, classId);
            if (success) {
                redirectAttributes.addFlashAttribute("success", "Sinh viên đã được hủy đăng ký khỏi lớp!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Sinh viên chưa đăng ký vào lớp này!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi hủy đăng ký!");
        }
        return "redirect:/enrollments/class/" + classId;
    }
}
