package com.example.j2ee.service;

import com.example.j2ee.model.StudentClass;
import com.example.j2ee.repository.StudentClassRepository;
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
} 