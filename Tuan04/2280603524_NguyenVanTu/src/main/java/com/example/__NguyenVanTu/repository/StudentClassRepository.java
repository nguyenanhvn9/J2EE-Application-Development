package com.example.__NguyenVanTu.repository;

import com.example.__NguyenVanTu.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
    
    List<StudentClass> findBySubjectId(Long subjectId);
    
    @Query("SELECT sc FROM StudentClass sc JOIN FETCH sc.subject s JOIN FETCH s.faculty WHERE sc.subject.id = :subjectId")
    List<StudentClass> findBySubjectIdWithSubject(Long subjectId);
    
    List<StudentClass> findByNameContainingIgnoreCase(String name);
}
