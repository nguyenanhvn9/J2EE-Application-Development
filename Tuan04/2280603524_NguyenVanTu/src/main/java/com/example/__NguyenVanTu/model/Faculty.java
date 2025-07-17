package com.example.__NguyenVanTu.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "faculties")
@Data
@ToString(exclude = "subjects")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subject> subjects;
    
    public Faculty() {}
    
    public Faculty(String name) {
        this.name = name;
    }
}
