package com.hutech.cos141_demo.BaiTH124.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hutech.cos141_demo.BaiTH124.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Basic CRUD operations are provided by JpaRepository
    // You can add custom query methods here if needed
} 