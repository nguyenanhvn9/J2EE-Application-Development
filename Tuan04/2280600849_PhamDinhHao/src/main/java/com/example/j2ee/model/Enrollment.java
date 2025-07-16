package com.example.j2ee.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "enrollments")
public class Enrollment implements Serializable {
    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("classId")
    @JoinColumn(name = "class_id")
    private StudentClass studentClass;

    public EnrollmentId getId() { return id; }
    public void setId(EnrollmentId id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public StudentClass getStudentClass() { return studentClass; }
    public void setStudentClass(StudentClass studentClass) { this.studentClass = studentClass; }
}