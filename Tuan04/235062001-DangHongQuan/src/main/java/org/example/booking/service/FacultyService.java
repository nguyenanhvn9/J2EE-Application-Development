package org.example.booking.service;

import org.example.booking.Response.FacultyRepository;
import org.example.booking.model.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    public Faculty findById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }
}
