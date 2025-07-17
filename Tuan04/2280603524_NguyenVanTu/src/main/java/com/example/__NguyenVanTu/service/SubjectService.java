package com.example.__NguyenVanTu.service;

import com.example.__NguyenVanTu.model.Subject;
import com.example.__NguyenVanTu.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }
    
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }
    
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }
    
    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }
    
    public List<Subject> findByFacultyId(Long facultyId) {
        return subjectRepository.findByFacultyId(facultyId);
    }
    
    public List<Subject> searchByName(String name) {
        return subjectRepository.findByNameContainingIgnoreCase(name);
    }
    
    public boolean existsById(Long id) {
        return subjectRepository.existsById(id);
    }
}
