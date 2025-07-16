package com.example.__NguyenNhatNam.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.example.__NguyenNhatNam.model.Book;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    public List<Book> getAllBooks() {
        return books;
    }

    public void addBook(Book book) {
        book.setId(nextId++);
        books.add(book);
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public void updateBook(Book updatedBook) {
        books.stream()
            .filter(book -> book.getId().equals(updatedBook.getId()))
            .findFirst()
            .ifPresent(book -> {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
            });
    }

    public void deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}
