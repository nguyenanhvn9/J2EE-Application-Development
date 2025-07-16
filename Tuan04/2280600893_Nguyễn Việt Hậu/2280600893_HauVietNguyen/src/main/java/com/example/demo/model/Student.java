package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;

    @ManyToMany(mappedBy = "students", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Set<StudentClass> classes = new HashSet<>();
}
