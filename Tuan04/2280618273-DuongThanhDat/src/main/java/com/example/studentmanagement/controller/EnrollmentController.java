package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.StudentClass;
import com.example.studentmanagement.model.Enrollment;
import com.example.studentmanagement.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    // Hiển thị trang quản lý đăng ký cho một lớp học
    @GetMapping("/{classId}")
    public String manageEnrollments(@PathVariable Long classId, Model model) {
        try {
            // Lấy thông tin lớp học
            StudentClass studentClass = enrollmentService.getClassWithEnrollments(classId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));
            
            // Lấy danh sách sinh viên đã đăng ký
            List<Enrollment> enrollments = enrollmentService.getActiveEnrollmentsByClass(classId);
            
            // Lấy danh sách sinh viên chưa đăng ký
            List<Student> availableStudents = enrollmentService.getStudentsNotInClass(classId);
            
            model.addAttribute("studentClass", studentClass);
            model.addAttribute("enrollments", enrollments);
            model.addAttribute("availableStudents", availableStudents);
            
            return "enrollments/manage";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải dữ liệu: " + e.getMessage());
            return "error";
        }
    }
    
    // Đăng ký sinh viên vào lớp học
    @PostMapping("/{classId}/enroll/{studentId}")
    public String enrollStudent(@PathVariable Long classId, 
                               @PathVariable Long studentId,
                               RedirectAttributes redirectAttributes) {
        try {
            boolean success = enrollmentService.enrollStudent(studentId, classId);
            if (success) {
                redirectAttributes.addFlashAttribute("success", "Đăng ký sinh viên thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Sinh viên đã được đăng ký hoặc có lỗi xảy ra!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi đăng ký: " + e.getMessage());
        }
        return "redirect:/enrollments/" + classId;
    }
    
    // Hủy đăng ký sinh viên khỏi lớp học
    @PostMapping("/{classId}/unenroll/{studentId}")
    public String unenrollStudent(@PathVariable Long classId, 
                                 @PathVariable Long studentId,
                                 RedirectAttributes redirectAttributes) {
        try {
            boolean success = enrollmentService.unenrollStudent(studentId, classId);
            if (success) {
                redirectAttributes.addFlashAttribute("success", "Hủy đăng ký sinh viên thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy đăng ký hoặc có lỗi xảy ra!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi hủy đăng ký: " + e.getMessage());
        }
        return "redirect:/enrollments/" + classId;
    }
    
    // Đăng ký nhiều sinh viên cùng lúc
    @PostMapping("/{classId}/enroll-multiple")
    public String enrollMultipleStudents(@PathVariable Long classId,
                                       @RequestParam(value = "studentIds", required = false) List<Long> studentIds,
                                       RedirectAttributes redirectAttributes) {
        try {
            if (studentIds == null || studentIds.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Vui lòng chọn ít nhất một sinh viên!");
                return "redirect:/enrollments/" + classId;
            }
            
            int successCount = 0;
            for (Long studentId : studentIds) {
                boolean success = enrollmentService.enrollStudent(studentId, classId);
                if (success) {
                    successCount++;
                }
            }
            
            redirectAttributes.addFlashAttribute("success", 
                "Đã đăng ký thành công " + successCount + "/" + studentIds.size() + " sinh viên!");
                
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi đăng ký: " + e.getMessage());
        }
        return "redirect:/enrollments/" + classId;
    }
}
