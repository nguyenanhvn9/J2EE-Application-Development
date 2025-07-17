package com.example.__NguyenVanTu.service;

import com.example.__NguyenVanTu.model.StudentClass;
import com.example.__NguyenVanTu.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentClassService {
    
    @Autowired
    private StudentClassRepository studentClassRepository;
    
    public List<StudentClass> findAll() {
        return studentClassRepository.findAll();
    }
    
    public Optional<StudentClass> findById(Long id) {
        return studentClassRepository.findById(id);
    }
    
    public StudentClass save(StudentClass studentClass) {
        return studentClassRepository.save(studentClass);
    }
    
    public void deleteById(Long id) {
        studentClassRepository.deleteById(id);
    }
    
    public List<StudentClass> findBySubjectId(Long subjectId) {
        return studentClassRepository.findBySubjectId(subjectId);
    }
    
    public List<StudentClass> searchByName(String name) {
        return studentClassRepository.findByNameContainingIgnoreCase(name);
    }
    
    public boolean existsById(Long id) {
        return studentClassRepository.existsById(id);
    }
}
