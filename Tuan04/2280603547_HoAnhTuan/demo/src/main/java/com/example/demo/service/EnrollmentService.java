package com.example.demo.service;

import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.model.StudentClass;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentClassRepository;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final StudentClassRepository studentClassRepository;

    public List<Enrollment> getEnrollmentsByClassId(Long classId) {
        return enrollmentRepository.findByStudentClassId(classId);
    }

    public List<Student> getStudentsNotInClass(Long classId) {
        List<Long> enrolledStudentIds = enrollmentRepository.findByStudentClassId(classId)
                .stream()
                .map(e -> e.getStudent().getId())
                .collect(Collectors.toList());
        return studentRepository.findAll()
                .stream()
                .filter(student -> !enrolledStudentIds.contains(student.getId()))
                .collect(Collectors.toList());
    }

    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<StudentClass> getClassesNotEnrolledByStudent(Long studentId) {
        List<Long> enrolledClassIds = enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(e -> e.getStudentClass().getId())
                .collect(Collectors.toList());
        return studentClassRepository.findAll()
                .stream()
                .filter(studentClass -> !enrolledClassIds.contains(studentClass.getId()))
                .collect(Collectors.toList());
    }

    public void enrollStudent(Long classId, Long studentId) {
        Enrollment enrollment = new Enrollment();
        StudentClass studentClass = studentClassRepository.findById(classId)
                .orElseThrow(() -> new IllegalStateException("StudentClass with ID " + classId + " does not exist."));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with ID " + studentId + " does not exist."));
        enrollment.setStudentClass(studentClass);
        enrollment.setStudent(student);
        enrollmentRepository.save(enrollment);
    }

    public void unenrollStudent(Long classId, Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentClassId(classId);
        Enrollment enrollment = enrollments.stream()
                .filter(e -> e.getStudent().getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Enrollment not found."));
        enrollmentRepository.delete(enrollment);
    }
}