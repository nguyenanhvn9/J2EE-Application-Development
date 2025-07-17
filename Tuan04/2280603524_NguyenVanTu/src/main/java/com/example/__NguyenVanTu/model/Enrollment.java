package com.example.__NguyenVanTu.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "enrollments")
@Data
@ToString(exclude = {"student", "studentClass"})
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private StudentClass studentClass;
    
    public Enrollment() {}
    
    public Enrollment(Student student, StudentClass studentClass) {
        this.student = student;
        this.studentClass = studentClass;
    }
}
