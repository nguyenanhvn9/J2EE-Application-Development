package com.hutech.cos141_demo.BaiQLSVTuan04.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hutech.cos141_demo.BaiQLSVTuan04.model.Enrollment;
import com.hutech.cos141_demo.BaiQLSVTuan04.model.EnrollmentId;
import com.hutech.cos141_demo.BaiQLSVTuan04.repository.EnrollmentRepository;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> getEnrollmentById(EnrollmentId id) {
        return enrollmentRepository.findById(id);
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollment(EnrollmentId id) {
        enrollmentRepository.deleteById(id);
    }
} 