package com.example.__NguyenVanTu.service;

import com.example.__NguyenVanTu.model.Enrollment;
import com.example.__NguyenVanTu.model.Student;
import com.example.__NguyenVanTu.model.StudentClass;
import com.example.__NguyenVanTu.repository.EnrollmentRepository;
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
    private StudentService studentService;
    
    @Autowired
    private StudentClassService studentClassService;
    
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }
    
    public Optional<Enrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }
    
    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
    
    public void deleteById(Long id) {
        enrollmentRepository.deleteById(id);
    }
    
    public boolean enrollStudent(Long studentId, Long classId) {
        if (enrollmentRepository.existsByStudentIdAndStudentClassId(studentId, classId)) {
            return false; // Already enrolled
        }
        
        Optional<Student> student = studentService.findById(studentId);
        Optional<StudentClass> studentClass = studentClassService.findById(classId);
        
        if (student.isPresent() && studentClass.isPresent()) {
            Enrollment enrollment = new Enrollment(student.get(), studentClass.get());
            enrollmentRepository.save(enrollment);
            return true;
        }
        return false;
    }
    
    public boolean unenrollStudent(Long studentId, Long classId) {
        if (enrollmentRepository.existsByStudentIdAndStudentClassId(studentId, classId)) {
            enrollmentRepository.deleteByStudentIdAndStudentClassId(studentId, classId);
            return true;
        }
        return false;
    }
    
    public boolean isStudentEnrolled(Long studentId, Long classId) {
        return enrollmentRepository.existsByStudentIdAndStudentClassId(studentId, classId);
    }
}
