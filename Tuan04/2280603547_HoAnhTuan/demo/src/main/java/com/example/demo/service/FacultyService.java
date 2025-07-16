package com.example.demo.service;

import com.example.demo.model.Faculty;
import com.example.demo.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Optional<Faculty> getFacultyById(Long id) {
        return facultyRepository.findById(id);
    }

    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFacultyById(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new IllegalStateException("Faculty with ID " + id + " does not exist.");
        }
        facultyRepository.deleteById(id);
    }
}