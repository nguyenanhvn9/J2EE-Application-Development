package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Faculty;
import com.example.studentmanagement.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacultyService {
    
    @Autowired
    private FacultyRepository facultyRepository;
    
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Faculty> getFacultiesWithFullHierarchy() {
        return facultyRepository.findAllWithSubjects();
    }
    
    public Optional<Faculty> getFacultyById(Long id) {
        return facultyRepository.findById(id);
    }
    
    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
    
    public List<Faculty> searchFaculties(String name) {
        return facultyRepository.findByNameContainingIgnoreCase(name);
    }
}
