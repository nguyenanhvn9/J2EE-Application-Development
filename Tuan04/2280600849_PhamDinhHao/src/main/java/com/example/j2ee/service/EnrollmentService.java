package com.example.j2ee.service;

import com.example.j2ee.model.Enrollment;
import com.example.j2ee.model.EnrollmentId;
import com.example.j2ee.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> findById(EnrollmentId id) {
        return enrollmentRepository.findById(id);
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteById(EnrollmentId id) {
        enrollmentRepository.deleteById(id);
    }
} 