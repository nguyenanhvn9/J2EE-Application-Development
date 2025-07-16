package com.example.j2ee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class StudentClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany
    @JoinTable(
        name = "enrollments",
        joinColumns = @JoinColumn(name = "class_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
    public java.util.List<Student> getStudents() { return students; }
    public void setStudents(java.util.List<Student> students) { this.students = students; }
} 