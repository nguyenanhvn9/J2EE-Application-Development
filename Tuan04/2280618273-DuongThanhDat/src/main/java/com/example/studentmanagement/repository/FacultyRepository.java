package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    
    @Query("SELECT DISTINCT f FROM Faculty f " +
           "LEFT JOIN FETCH f.subjects s " +
           "LEFT JOIN FETCH s.studentClasses sc " +
           "LEFT JOIN FETCH sc.enrollments e " +
           "LEFT JOIN FETCH e.student")
    List<Faculty> findAllWithSubjects();
    
    List<Faculty> findByNameContainingIgnoreCase(String name);
}
