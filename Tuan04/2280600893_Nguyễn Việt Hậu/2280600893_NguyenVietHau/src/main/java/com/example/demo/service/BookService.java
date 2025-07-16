package com.example.demo.service;

import com.example.demo.model.Books;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Lấy tất cả sách
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    // Lấy sách theo ID
    public Optional<Books> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    // Tìm sách theo tác giả
    public List<Books> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    // Thêm hoặc cập nhật sách
    public Books saveBook(Books book) {
        return bookRepository.save(book);
    }

    // Xóa sách theo ID
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    // Kiểm tra sách có tồn tại không
    public boolean exists(UUID id) {
        return bookRepository.existsById(id);
    }
}
