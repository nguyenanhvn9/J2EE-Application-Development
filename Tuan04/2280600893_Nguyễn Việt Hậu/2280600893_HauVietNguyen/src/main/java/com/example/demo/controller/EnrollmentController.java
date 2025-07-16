package com.example.demo.controller;

import com.example.demo.model.EnrollmentForm;
import org.springframework.ui.Model;
import com.example.demo.model.Student;
import com.example.demo.model.StudentClass;
import com.example.demo.service.StudentClassService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentClassService classService;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "enrollment/index"; // view ban đầu có select student
    }

    @GetMapping("/{studentId}")
    public String showClassesForStudent(@PathVariable UUID studentId, Model model) {
        Student student = studentService.findById(studentId);
        Set<StudentClass> registered = new HashSet<>(student.getClasses());
        List<StudentClass> all = classService.findAll();

        model.addAttribute("studentId", studentId);
        model.addAttribute("registered", registered);
        model.addAttribute("available", all.stream()
                .filter(c -> !registered.contains(c))
                .toList());

        return "enrollment/form"; // giao diện giống hình bạn gửi
    }

    @PostMapping
    public String saveEnrollment(@ModelAttribute EnrollmentForm form) {
        Student student = studentService.findById(form.getStudentId());

        // Xóa các quan hệ cũ
        student.getClasses().forEach(c -> c.getStudents().remove(student));
        student.getClasses().clear();

        // Lấy danh sách lớp mới
        Set<StudentClass> selectedClasses = form.getEnrolledClassIds().stream()
                .map(id -> classService.findById(id))
                .collect(Collectors.toSet());

        // Cập nhật cả hai chiều
        for (StudentClass clazz : selectedClasses) {
            clazz.getStudents().add(student);
        }

        student.setClasses(selectedClasses);
        studentService.save(student); // cập nhật cả student và quan hệ ManyToMany

        return "redirect:/enrollments";
    }
}
