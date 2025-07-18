package com.example.studentmanagement.service;

import com.example.studentmanagement.model.StudentClass;
import com.example.studentmanagement.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentClassService {
    
    @Autowired
    private StudentClassRepository studentClassRepository;
    
    public List<StudentClass> getAllStudentClasses() {
        return studentClassRepository.findAll();
    }
    
    public Optional<StudentClass> getStudentClassById(Long id) {
        return studentClassRepository.findById(id);
    }
    
    public StudentClass saveStudentClass(StudentClass studentClass) {
        return studentClassRepository.save(studentClass);
    }
    
    public void deleteStudentClass(Long id) {
        studentClassRepository.deleteById(id);
    }
    
    public List<StudentClass> getStudentClassesBySubjectId(Long subjectId) {
        return studentClassRepository.findBySubjectId(subjectId);
    }
    
    public List<StudentClass> searchStudentClasses(String name) {
        return studentClassRepository.findByNameContainingIgnoreCase(name);
    }
    
    public StudentClass getStudentClassWithStudents(Long id) {
        return studentClassRepository.findByIdWithStudents(id);
    }
}
