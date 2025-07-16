package com.example.j2ee.repository;

import com.example.j2ee.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
} 