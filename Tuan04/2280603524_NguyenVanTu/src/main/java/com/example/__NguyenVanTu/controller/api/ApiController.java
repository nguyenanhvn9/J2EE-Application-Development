package com.example.__NguyenVanTu.controller.api;

import com.example.__NguyenVanTu.model.Student;
import com.example.__NguyenVanTu.model.StudentClass;
import com.example.__NguyenVanTu.service.StudentClassService;
import com.example.__NguyenVanTu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private StudentClassService studentClassService;
    
    @GetMapping("/students/{studentId}/enrollment-data")
    public ResponseEntity<Map<String, Object>> getStudentEnrollmentData(@PathVariable Long studentId) {
        try {
            List<StudentClass> allClasses = studentClassService.findAll();
            
            // Convert to simplified objects for JSON
            List<Map<String, Object>> available = allClasses.stream()
                .filter(cls -> !isStudentEnrolledInClass(studentId, cls.getId()))
                .map(cls -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", cls.getId());
                    map.put("name", cls.getName());
                    map.put("subjectName", cls.getSubject().getName());
                    map.put("facultyName", cls.getSubject().getFaculty().getName());
                    return map;
                })
                .collect(Collectors.toList());
                
            List<Map<String, Object>> registered = allClasses.stream()
                .filter(cls -> isStudentEnrolledInClass(studentId, cls.getId()))
                .map(cls -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", cls.getId());
                    map.put("name", cls.getName());
                    map.put("subjectName", cls.getSubject().getName());
                    map.put("facultyName", cls.getSubject().getFaculty().getName());
                    return map;
                })
                .collect(Collectors.toList());
            
            Map<String, Object> result = new HashMap<>();
            result.put("available", available);
            result.put("registered", registered);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("available", List.of());
            errorResult.put("registered", List.of());
            return ResponseEntity.ok(errorResult);
        }
    }
    
    private boolean isStudentEnrolledInClass(Long studentId, Long classId) {
        List<Student> enrolledStudents = studentService.findStudentsEnrolledInClass(classId);
        return enrolledStudents.stream().anyMatch(student -> student.getId().equals(studentId));
    }
}
