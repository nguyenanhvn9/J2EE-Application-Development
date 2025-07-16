package com.hutech.cos141_demo.BaiQLSVTuan04.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hutech.cos141_demo.BaiQLSVTuan04.model.Enrollment;
import com.hutech.cos141_demo.BaiQLSVTuan04.model.EnrollmentId;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
} 