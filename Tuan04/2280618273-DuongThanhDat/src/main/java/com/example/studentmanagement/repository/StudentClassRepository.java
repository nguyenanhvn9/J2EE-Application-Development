package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
    List<StudentClass> findBySubjectId(Long subjectId);
    List<StudentClass> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT sc FROM StudentClass sc LEFT JOIN FETCH sc.enrollments WHERE sc.id = ?1")
    StudentClass findByIdWithStudents(Long id);
}
