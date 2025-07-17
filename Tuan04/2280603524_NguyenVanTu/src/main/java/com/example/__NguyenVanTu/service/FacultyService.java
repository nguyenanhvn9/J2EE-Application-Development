package com.example.__NguyenVanTu.service;

import com.example.__NguyenVanTu.model.Faculty;
import com.example.__NguyenVanTu.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    
    @Autowired
    private FacultyRepository facultyRepository;
    
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }
    
    public List<Faculty> findAllWithCompleteData() {
        return facultyRepository.findAllWithCompleteData();
    }
    
    public Optional<Faculty> findById(Long id) {
        return facultyRepository.findById(id);
    }
    
    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    
    public void deleteById(Long id) {
        facultyRepository.deleteById(id);
    }
    
    public List<Faculty> searchByName(String name) {
        return facultyRepository.findByNameContainingIgnoreCase(name);
    }
    
    public boolean existsById(Long id) {
        return facultyRepository.existsById(id);
    }
}
