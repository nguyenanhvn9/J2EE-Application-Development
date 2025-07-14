package com.example.BaitapJ2EE.QuanLySach.repository;

import com.example.BaitapJ2EE.QuanLySach.model.Book;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class BookRepository {
    private List<Book> books = new ArrayList<>();
    private long nextId = 1;

    public BookRepository() {
        // Thêm dữ liệu mẫu để tránh danh sách rỗng và kiểm tra lỗi
        books.add(new Book(nextId++, "Spring boot", "Huy Cuong"));
        books.add(new Book(nextId++, "Spring Boot V2", "Anh"));
    }

    public List<Book> findAll() { return books; }
    public Book findById(Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }
    public void save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId++);
            books.add(book);
        } else {
            Book existing = findById(book.getId());
            if (existing != null) {
                existing.setTitle(book.getTitle());
                existing.setAuthor(book.getAuthor());
            }
        }
    }
    public void deleteById(Long id) {
        books.removeIf(b -> b.getId().equals(id));
    }
} 