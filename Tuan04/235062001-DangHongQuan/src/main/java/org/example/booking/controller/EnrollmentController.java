//package org.example.booking.controller;
//
//import org.example.booking.model.Student;
//import org.example.booking.model.StudentClass;
//import org.example.booking.service.EnrollmentService;
//import org.example.booking.service.StudentClassService;
//import org.example.booking.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/enrollments")
//public class EnrollmentController {
//    @Autowired
//    private EnrollmentService enrollmentService;
//
//    @Autowired
//    private StudentService studentService;
//
//    @Autowired
//    private StudentClassService studentClassService;
//
//    @GetMapping("/{classId}")
//    public String manageEnrollment(@PathVariable Long classId, Model model) {
//        StudentClass studentClass = studentClassService.findById(classId);
//        List<Student> enrolledStudents = enrollmentService.findEnrolledStudents(studentClass);
//        List<Student> notEnrolledStudents = enrollmentService.findNotEnrolledStudents(studentClass);
//
//        model.addAttribute("studentClass", studentClass);
//        model.addAttribute("enrolledStudents", enrolledStudents);
//        model.addAttribute("notEnrolledStudents", notEnrolledStudents);
//        return "enrollment/manage";
//    }
//
//    @PostMapping("/enroll")
//    public String enrollStudents(@RequestParam Long classId, @RequestParam List<Long> studentIds) {
//        StudentClass studentClass = studentClassService.findById(classId);
//
//        for (Long studentId : studentIds) {
//            Student student = studentService.findById(studentId);
//            enrollmentService.enrollStudent(studentClass, student);
//        }
//
//        return "redirect:/enrollments/" + classId;
//    }
//
//    @PostMapping("/unenroll/{enrollmentId}")
//    public String unenrollStudent(@PathVariable Long enrollmentId) {
//        enrollmentService.unenrollStudent(enrollmentId);
//        return "redirect:/enrollments/";
//    }
//}
