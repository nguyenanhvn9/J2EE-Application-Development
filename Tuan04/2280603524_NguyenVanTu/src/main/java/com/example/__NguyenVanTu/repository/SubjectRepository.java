package com.example.__NguyenVanTu.repository;

import com.example.__NguyenVanTu.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
    List<Subject> findByFacultyId(Long facultyId);
    
    @Query("SELECT s FROM Subject s JOIN FETCH s.faculty WHERE s.faculty.id = :facultyId")
    List<Subject> findByFacultyIdWithFaculty(Long facultyId);
    
    List<Subject> findByNameContainingIgnoreCase(String name);
}
