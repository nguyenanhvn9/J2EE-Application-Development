package com.example.j2ee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public java.util.List<Subject> getSubjects() { return subjects; }
    public void setSubjects(java.util.List<Subject> subjects) { this.subjects = subjects; }
} 