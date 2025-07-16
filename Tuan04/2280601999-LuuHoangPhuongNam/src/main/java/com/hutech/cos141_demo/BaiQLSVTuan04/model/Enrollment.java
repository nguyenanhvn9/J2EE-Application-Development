package com.hutech.cos141_demo.BaiQLSVTuan04.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id = new EnrollmentId();

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("studentClassId")
    @JoinColumn(name = "student_class_id")
    private StudentClass studentClass;

    public Enrollment() {}
    public Enrollment(Student student, StudentClass studentClass) {
        this.student = student;
        this.studentClass = studentClass;
        this.id = new EnrollmentId(student.getId(), studentClass.getId());
    }
    public EnrollmentId getId() { return id; }
    public void setId(EnrollmentId id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public StudentClass getStudentClass() { return studentClass; }
    public void setStudentClass(StudentClass studentClass) { this.studentClass = studentClass; }
} 