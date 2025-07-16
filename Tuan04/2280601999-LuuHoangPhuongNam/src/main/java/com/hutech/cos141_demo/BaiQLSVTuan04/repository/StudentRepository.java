package com.hutech.cos141_demo.BaiQLSVTuan04.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hutech.cos141_demo.BaiQLSVTuan04.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
} 