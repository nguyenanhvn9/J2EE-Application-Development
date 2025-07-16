package com.hutech.cos141_demo.BaiQLSVTuan04.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hutech.cos141_demo.BaiQLSVTuan04.model.Enrollment;
import com.hutech.cos141_demo.BaiQLSVTuan04.model.EnrollmentId;
import com.hutech.cos141_demo.BaiQLSVTuan04.model.Student;
import com.hutech.cos141_demo.BaiQLSVTuan04.model.StudentClass;
import com.hutech.cos141_demo.BaiQLSVTuan04.service.EnrollmentService;
import com.hutech.cos141_demo.BaiQLSVTuan04.service.StudentClassService;
import com.hutech.cos141_demo.BaiQLSVTuan04.service.StudentService;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentClassService studentClassService;

    @GetMapping
    public String selectClass(Model model) {
        model.addAttribute("classes", studentClassService.getAllStudentClasses());
        return "select-class";
    }

    @GetMapping("/manage/{classId}")
    public String manageEnrollments(@PathVariable Long classId, Model model) {
        StudentClass studentClass = studentClassService.getStudentClassById(classId).orElseThrow();
        List<Enrollment> enrollments = studentClass.getEnrollments();
        List<Student> registered = enrollments.stream().map(Enrollment::getStudent).collect(Collectors.toList());
        List<Student> allStudents = studentService.getAllStudents();
        List<Student> notRegistered = allStudents.stream().filter(s -> !registered.contains(s)).collect(Collectors.toList());
        model.addAttribute("studentClass", studentClass);
        model.addAttribute("registered", registered);
        model.addAttribute("notRegistered", notRegistered);
        return "manage-enrollments";
    }

    @PostMapping("/add")
    public String addEnrollment(@RequestParam Long classId, @RequestParam List<Long> studentIds) {
        StudentClass studentClass = studentClassService.getStudentClassById(classId).orElseThrow();
        for (Long studentId : studentIds) {
            Student student = studentService.getStudentById(studentId).orElseThrow();
            Enrollment enrollment = new Enrollment(student, studentClass);
            enrollmentService.saveEnrollment(enrollment);
        }
        return "redirect:/enrollments/manage/" + classId;
    }

    @PostMapping("/remove")
    public String removeEnrollment(@RequestParam Long classId, @RequestParam Long studentId) {
        EnrollmentId id = new EnrollmentId(studentId, classId);
        enrollmentService.deleteEnrollment(id);
        return "redirect:/enrollments/manage/" + classId;
    }

    @GetMapping("/add-by-student")
    public String addEnrollmentByStudent(@RequestParam(value = "studentId", required = false) Long studentId, Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("selectedStudentId", studentId);
        List<StudentClass> availableClasses;
        List<StudentClass> registeredClasses;
        if (studentId != null) {
            Student student = studentService.getStudentById(studentId).orElse(null);
            if (student != null) {
                List<Enrollment> enrollments = student.getEnrollments();
                List<StudentClass> tempRegistered = enrollments.stream().map(Enrollment::getStudentClass).collect(Collectors.toList());
                List<StudentClass> allClasses = studentClassService.getAllStudentClasses();
                List<StudentClass> tempAvailable = allClasses.stream().filter(c -> !tempRegistered.contains(c)).collect(Collectors.toList());
                registeredClasses = tempRegistered;
                availableClasses = tempAvailable;
            } else {
                registeredClasses = new ArrayList<>();
                availableClasses = studentClassService.getAllStudentClasses();
            }
        } else {
            registeredClasses = new ArrayList<>();
            availableClasses = studentClassService.getAllStudentClasses();
        }
        model.addAttribute("availableClasses", availableClasses);
        model.addAttribute("registeredClasses", registeredClasses);
        return "BaiQLSVTuan04/add-enrollment";
    }

    @PostMapping("/add-by-student")
    public String saveEnrollmentByStudent(@RequestParam Long studentId, @RequestParam(required = false) List<Long> classIds) {
        Student student = studentService.getStudentById(studentId).orElseThrow();
        // Xóa hết đăng ký cũ
        List<Enrollment> currentEnrollments = new ArrayList<>(student.getEnrollments());
        for (Enrollment e : currentEnrollments) {
            enrollmentService.deleteEnrollment(e.getId());
        }
        // Thêm đăng ký mới
        if (classIds != null) {
            for (Long classId : classIds) {
                StudentClass studentClass = studentClassService.getStudentClassById(classId).orElseThrow();
                Enrollment enrollment = new Enrollment(student, studentClass);
                enrollmentService.saveEnrollment(enrollment);
            }
        }
        return "redirect:/enrollments/add-by-student?studentId=" + studentId;
    }
} 