package com.example.__NguyenVanTu.repository;

import com.example.__NguyenVanTu.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    void deleteByStudentIdAndStudentClassId(Long studentId, Long classId);
    
    boolean existsByStudentIdAndStudentClassId(Long studentId, Long classId);
}
