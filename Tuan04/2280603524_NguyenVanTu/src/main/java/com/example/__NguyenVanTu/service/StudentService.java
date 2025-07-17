package com.example.__NguyenVanTu.service;

import com.example.__NguyenVanTu.model.Student;
import com.example.__NguyenVanTu.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Student save(Student student) {
        return studentRepository.save(student);
    }
    
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
    
    public List<Student> searchByName(String name) {
        return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
    
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
    
    public List<Student> findStudentsNotEnrolledInClass(Long classId) {
        return studentRepository.findStudentsNotEnrolledInClass(classId);
    }
    
    public List<Student> findStudentsEnrolledInClass(Long classId) {
        return studentRepository.findStudentsEnrolledInClass(classId);
    }
    
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }
}
