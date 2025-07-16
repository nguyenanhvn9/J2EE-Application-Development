package com.example.j2ee.repository;

import com.example.j2ee.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
} 