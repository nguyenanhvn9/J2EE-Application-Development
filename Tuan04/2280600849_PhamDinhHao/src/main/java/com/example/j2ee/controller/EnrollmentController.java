package com.example.j2ee.controller;

import com.example.j2ee.model.*;
import com.example.j2ee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
    @Autowired
    private StudentClassService studentClassService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public String selectClass(Model model) {
        model.addAttribute("classes", studentClassService.findAll());
        return "enrollment_select_class";
    }

    @GetMapping("/manage/{classId}")
    public String manageEnrollment(@PathVariable Long classId, Model model) {
        StudentClass studentClass = studentClassService.findById(classId).orElseThrow();
        List<Student> allStudents = studentService.findAll();
        List<Student> enrolled = studentClass.getStudents();
        List<Student> notEnrolled = allStudents.stream().filter(s -> !enrolled.contains(s)).collect(Collectors.toList());
        model.addAttribute("studentClass", studentClass);
        model.addAttribute("enrolled", enrolled);
        model.addAttribute("notEnrolled", notEnrolled);
        return "enrollment_manage";
    }

    @PostMapping("/add")
    public String addStudentToClass(@RequestParam Long classId, @RequestParam List<Long> studentIds) {
        StudentClass studentClass = studentClassService.findById(classId).orElseThrow();
        for (Long studentId : studentIds) {
            Student student = studentService.findById(studentId).orElseThrow();
            studentClass.getStudents().add(student);
        }
        studentClassService.save(studentClass);
        return "redirect:/enrollments/manage/" + classId;
    }

    @PostMapping("/remove")
    public String removeStudentFromClass(@RequestParam Long classId, @RequestParam Long studentId) {
        StudentClass studentClass = studentClassService.findById(classId).orElseThrow();
        Student student = studentService.findById(studentId).orElseThrow();
        studentClass.getStudents().remove(student);
        studentClassService.save(studentClass);
        return "redirect:/enrollments/manage/" + classId;
    }
} 