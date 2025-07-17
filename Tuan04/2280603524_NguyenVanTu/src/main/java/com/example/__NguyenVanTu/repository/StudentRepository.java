package com.example.__NguyenVanTu.repository;

import com.example.__NguyenVanTu.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    
    Student findByEmail(String email);
    
    @Query("SELECT s FROM Student s WHERE s.id NOT IN (SELECT e.student.id FROM Enrollment e WHERE e.studentClass.id = :classId)")
    List<Student> findStudentsNotEnrolledInClass(Long classId);
    
    @Query("SELECT s FROM Student s JOIN s.enrollments e WHERE e.studentClass.id = :classId")
    List<Student> findStudentsEnrolledInClass(Long classId);
}
