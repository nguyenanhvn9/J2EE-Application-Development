package org.example.booking.controller;

import org.example.booking.model.*;
import org.example.booking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private EnrollmentService enrollmentService;

    // ===== TRANG CHỦ =====
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("faculties", facultyService.findAll());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("studentClasses", studentClassService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        return "index";
    }

    // ===== QUẢN LÝ KHOA =====
    @GetMapping("/faculties")
    public String listFaculties(Model model) {
        model.addAttribute("faculties", facultyService.findAll());
        return "faculty/list";
    }

    @GetMapping("/faculties/new")
    public String createFacultyForm(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "faculty/form";
    }

    @PostMapping("/faculties")
    public String saveFaculty(@ModelAttribute Faculty faculty) {
        facultyService.save(faculty);
        return "redirect:/";
    }

    @GetMapping("/faculties/edit/{id}")
    public String editFacultyForm(@PathVariable Long id, Model model) {
        Faculty faculty = facultyService.findById(id);
        model.addAttribute("faculty", faculty);
        return "faculty/form";
    }

    @PostMapping("/faculties/edit")
    public String updateFaculty(@ModelAttribute Faculty faculty) {
        facultyService.save(faculty);
        return "redirect:/";
    }

    @GetMapping("/faculties/delete/{id}")
    public String deleteFaculty(@PathVariable Long id) {
        facultyService.delete(id);
        return "redirect:/";
    }

    // ===== QUẢN LÝ SINH VIÊN =====
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/list";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/form";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "student/form";
    }

    @PostMapping("/students/edit")
    public String updateStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/";
    }

    // ===== QUẢN LÝ LỚP HỌC =====
    @GetMapping("/student-classes")
    public String listStudentClasses(Model model) {
        model.addAttribute("studentClasses", studentClassService.findAll());
        return "studentClass/list";
    }

    @GetMapping("/student-classes/new")
    public String createStudentClassForm(Model model) {
        model.addAttribute("studentClass", new StudentClass());
        return "studentClass/form";
    }

    @PostMapping("/student-classes")
    public String saveStudentClass(@ModelAttribute StudentClass studentClass) {
        studentClassService.save(studentClass);
        return "redirect:/";
    }

    @GetMapping("/student-classes/edit/{id}")
    public String editStudentClassForm(@PathVariable Long id, Model model) {
        StudentClass studentClass = studentClassService.findById(id);
        model.addAttribute("studentClass", studentClass);
        return "studentClass/form";
    }

    @PostMapping("/student-classes/edit")
    public String updateStudentClass(@ModelAttribute StudentClass studentClass) {
        studentClassService.save(studentClass);
        return "redirect:/";
    }

    @GetMapping("/student-classes/delete/{id}")
    public String deleteStudentClass(@PathVariable Long id) {
        studentClassService.delete(id);
        return "redirect:/";
    }

    // ===== QUẢN LÝ MÔN HỌC =====
    @GetMapping("/subjects")
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subject/list";
    }

    @GetMapping("/subjects/new")
    public String createSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject/form";
    }

    @PostMapping("/subjects")
    public String saveSubject(@ModelAttribute Subject subject) {
        subjectService.save(subject);
        return "redirect:/";
    }

    @GetMapping("/subjects/edit/{id}")
    public String editSubjectForm(@PathVariable Long id, Model model) {
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);
        return "subject/form";
    }

    @PostMapping("/subjects/edit")
    public String updateSubject(@ModelAttribute Subject subject) {
        subjectService.save(subject);
        return "redirect:/";
    }

    @GetMapping("/subjects/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.delete(id);
        return "redirect:/";
    }

    // ===== QUẢN LÝ ĐĂNG KÝ =====
    @GetMapping("/manage-enrollment/{classId}")
    public String manageEnrollment(@PathVariable Long classId, Model model) {
        StudentClass studentClass = studentClassService.findById(classId);
        List<Student> enrolledStudents = enrollmentService.findEnrolledStudents(studentClass);
        List<Student> notEnrolledStudents = enrollmentService.findNotEnrolledStudents(studentClass);

        model.addAttribute("studentClass", studentClass);
        model.addAttribute("enrolledStudents", enrolledStudents);
        model.addAttribute("notEnrolledStudents", notEnrolledStudents);
        return "enrollment/manage";
    }

    @PostMapping("/enroll-students")
    public String enrollStudents(@RequestParam Long classId, @RequestParam List<Long> studentIds) {
        StudentClass studentClass = studentClassService.findById(classId);

        for (Long studentId : studentIds) {
            Student student = studentService.findById(studentId);
            enrollmentService.enrollStudent(studentClass, student);
        }

        return "redirect:/manage-enrollment/" + classId;
    }

    @PostMapping("/unenroll-student/{enrollmentId}")
    public String unenrollStudent(@PathVariable Long enrollmentId) {
        enrollmentService.unenrollStudent(enrollmentId);
        return "redirect:/";
    }
}