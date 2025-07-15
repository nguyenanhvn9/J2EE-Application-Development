package com.example.QLS.service;

import com.example.QLS.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookViewService {
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
