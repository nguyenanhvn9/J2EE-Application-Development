package com.hutech.cos141_demo.BaiTH124.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 500)
    private String title;
    
    @Column(length = 200)
    private String author;
    
    @Column(length = 100)
    private String language;
    
    @Column(length = 50)
    private String downloadCount;
    
    @Column(length = 1000)
    private String subjects;
    
    @Column(length = 500)
    private String bookshelves;
    
    @Column(length = 1000)
    private String formats;
    
    @Column(length = 50)
    private String gutenbergId;
} 