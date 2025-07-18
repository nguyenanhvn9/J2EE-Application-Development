package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Enrollment;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.StudentClass;
import com.example.studentmanagement.repository.EnrollmentRepository;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentClassRepository studentClassRepository;
    
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
    
    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
    
    public List<Enrollment> getEnrollmentsByClass(Long classId) {
        return enrollmentRepository.findByStudentClassId(classId);
    }
    
    public List<Enrollment> getActiveEnrollmentsByClass(Long classId) {
        return enrollmentRepository.findActiveEnrollmentsByClassId(classId);
    }
    
    public boolean enrollStudent(Long studentId, Long classId) {
        try {
            // Kiểm tra xem sinh viên đã đăng ký lớp này chưa
            Optional<Enrollment> existingEnrollment = enrollmentRepository.findByStudentIdAndClassId(studentId, classId);
            if (existingEnrollment.isPresent()) {
                return false; // Đã đăng ký rồi
            }
            
            // Lấy thông tin sinh viên và lớp học
            Optional<Student> studentOpt = studentRepository.findById(studentId);
            Optional<StudentClass> classOpt = studentClassRepository.findById(classId);
            
            if (studentOpt.isPresent() && classOpt.isPresent()) {
                Enrollment enrollment = new Enrollment(studentOpt.get(), classOpt.get());
                enrollmentRepository.save(enrollment);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đăng ký sinh viên: " + e.getMessage());
        }
    }
    
    public boolean unenrollStudent(Long studentId, Long classId) {
        try {
            Optional<Enrollment> enrollment = enrollmentRepository.findByStudentIdAndClassId(studentId, classId);
            if (enrollment.isPresent()) {
                enrollmentRepository.delete(enrollment.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi hủy đăng ký sinh viên: " + e.getMessage());
        }
    }
    
    public List<Student> getStudentsNotInClass(Long classId) {
        // Lấy tất cả sinh viên
        List<Student> allStudents = studentRepository.findAll();
        
        // Lấy danh sách sinh viên đã đăng ký lớp này
        List<Enrollment> enrollments = enrollmentRepository.findActiveEnrollmentsByClassId(classId);
        List<Long> enrolledStudentIds = enrollments.stream()
                .map(e -> e.getStudent().getId())
                .collect(java.util.stream.Collectors.toList());
        
        // Lọc ra những sinh viên chưa đăng ký
        return allStudents.stream()
                .filter(student -> !enrolledStudentIds.contains(student.getId()))
                .collect(java.util.stream.Collectors.toList());
    }
    
    public Optional<StudentClass> getClassWithEnrollments(Long classId) {
        return studentClassRepository.findById(classId);
    }
}
