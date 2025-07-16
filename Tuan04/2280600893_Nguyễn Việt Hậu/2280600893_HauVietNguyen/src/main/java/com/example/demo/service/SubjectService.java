package com.example.demo.service;

import com.example.demo.model.Subject;
import com.example.demo.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public List<Subject> findByFacultyId(UUID facultyId) {
        return subjectRepository.findByFacultyId(facultyId);
    }

    public Subject findById(UUID id) {
        return subjectRepository.findById(id).orElse(null);
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteById(UUID id) {
        subjectRepository.deleteById(id);
    }
}

