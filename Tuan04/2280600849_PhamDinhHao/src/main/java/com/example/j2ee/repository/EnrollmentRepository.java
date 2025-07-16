package com.example.j2ee.repository;

import com.example.j2ee.model.Enrollment;
import com.example.j2ee.model.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
} 