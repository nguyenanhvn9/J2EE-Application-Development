package com.example.demo.controller;

import com.example.demo.service.EnrollmentService;
import com.example.demo.service.StudentClassService;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final StudentClassService studentClassService;
    private final StudentService studentService;

    @GetMapping("/classes/{classId}")
    public String manageEnrollmentsByClass(@PathVariable Long classId, Model model) {
        model.addAttribute("studentClass", studentClassService.getStudentClassById(classId)
                .orElseThrow(() -> new IllegalStateException("StudentClass with ID " + classId + " does not exist.")));
        model.addAttribute("enrolledStudents", enrollmentService.getEnrollmentsByClassId(classId)
                .stream().map(e -> e.getStudent()).toList());
        model.addAttribute("availableStudents", enrollmentService.getStudentsNotInClass(classId));
        return "enrollments/manage-enrollments";
    }

    @PostMapping("/classes/{classId}/add/{studentId}")
    public String addEnrollmentToClass(@PathVariable Long classId, @PathVariable Long studentId) {
        enrollmentService.enrollStudent(classId, studentId);
        return "redirect:/enrollments/classes/" + classId;
    }

    @PostMapping("/classes/{classId}/remove/{studentId}")
    public String removeEnrollmentFromClass(@PathVariable Long classId, @PathVariable Long studentId) {
        enrollmentService.unenrollStudent(classId, studentId);
        return "redirect:/enrollments/classes/" + classId;
    }

    @GetMapping("/students/{studentId}")
    public String manageEnrollmentsByStudent(@PathVariable Long studentId, Model model) {
        model.addAttribute("student", studentService.getStudentById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with ID " + studentId + " does not exist.")));
        model.addAttribute("enrolledClasses", enrollmentService.getEnrollmentsByStudentId(studentId)
                .stream().map(e -> e.getStudentClass()).toList());
        model.addAttribute("availableClasses", enrollmentService.getClassesNotEnrolledByStudent(studentId));
        return "enrollments/manage-student-enrollments";
    }

    @PostMapping("/students/{studentId}/add/{classId}")
    public String addEnrollmentToStudent(@PathVariable Long studentId, @PathVariable Long classId) {
        enrollmentService.enrollStudent(classId, studentId);
        return "redirect:/enrollments/students/" + studentId;
    }

    @PostMapping("/students/{studentId}/remove/{classId}")
    public String removeEnrollmentFromStudent(@PathVariable Long studentId, @PathVariable Long classId) {
        enrollmentService.unenrollStudent(classId, studentId);
        return "redirect:/enrollments/students/" + studentId;
    }
}