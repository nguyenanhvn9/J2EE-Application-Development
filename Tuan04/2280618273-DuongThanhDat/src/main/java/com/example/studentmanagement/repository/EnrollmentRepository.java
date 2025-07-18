package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    List<Enrollment> findByStudentId(Long studentId);
    
    List<Enrollment> findByStudentClassId(Long classId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = ?1 AND e.studentClass.id = ?2")
    Optional<Enrollment> findByStudentIdAndClassId(Long studentId, Long classId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.studentClass.id = ?1 AND e.status = 'ACTIVE'")
    List<Enrollment> findActiveEnrollmentsByClassId(Long classId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = ?1 AND e.status = 'ACTIVE'")
    List<Enrollment> findActiveEnrollmentsByStudentId(Long studentId);
    
    void deleteByStudentIdAndStudentClassId(Long studentId, Long classId);
}
