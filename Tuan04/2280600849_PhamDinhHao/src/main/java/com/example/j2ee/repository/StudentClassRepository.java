package com.example.j2ee.repository;

import com.example.j2ee.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
} 