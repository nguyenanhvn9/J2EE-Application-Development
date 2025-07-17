package com.example.__NguyenVanTu.repository;

import com.example.__NguyenVanTu.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    
    @Query("SELECT DISTINCT f FROM Faculty f LEFT JOIN FETCH f.subjects")
    List<Faculty> findAllWithCompleteData();
    
    List<Faculty> findByNameContainingIgnoreCase(String name);
}
