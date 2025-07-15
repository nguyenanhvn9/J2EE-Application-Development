package com.example.QLySach_J2EE.service;

import com.example.QLySach_J2EE.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    public BookService() {
        // Thêm một số sách mẫu
        try {
            Book sampleBook = new Book("Phat trien Ung dung voi J2EE", "Nguyễn Huy Cường");
            sampleBook.setId(nextId++);
            books.add(sampleBook);
            
            Book sampleBook2 = new Book("Spring Boot in Action", "Craig Walls");
            sampleBook2.setId(nextId++);
            books.add(sampleBook2);
            
            System.out.println("BookService initialized with " + books.size() + " sample books");
        } catch (Exception e) {
            System.err.println("Error initializing BookService: " + e.getMessage());
            books = new ArrayList<>();
            nextId = 1L;
        }
    }

    public List<Book> getAllBooks() {
        try {
            if (books == null) {
                books = new ArrayList<>();
            }
            System.out.println("Getting all books, count: " + books.size());
            return new ArrayList<>(books);
        } catch (Exception e) {
            System.err.println("Error getting all books: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void addBook(Book book) {
        try {
            if (book != null) {
                if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
                    book.setTitle("Untitled Book");
                }
                if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
                    book.setAuthor("Unknown Author");
                }
                book.setId(nextId++);
                books.add(book);
                System.out.println("Added book: " + book.getTitle());
            }
        } catch (Exception e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    public Optional<Book> getBookById(Long id) {
        try {
            if (id != null && books != null) {
                return books.stream()
                        .filter(book -> book != null && id.equals(book.getId()))
                        .findFirst();
            }
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("Error getting book by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void updateBook(Book updatedBook) {
        try {
            if (updatedBook != null && updatedBook.getId() != null && books != null) {
                books.stream()
                        .filter(book -> book != null && book.getId().equals(updatedBook.getId()))
                        .findFirst()
                        .ifPresent(book -> {
                            book.setTitle(updatedBook.getTitle() != null ? updatedBook.getTitle() : book.getTitle());
                            book.setAuthor(updatedBook.getAuthor() != null ? updatedBook.getAuthor() : book.getAuthor());
                        });
                System.out.println("Updated book with ID: " + updatedBook.getId());
            }
        } catch (Exception e) {
            System.err.println("Error updating book: " + e.getMessage());
        }
    }

    public void deleteBook(Long id) {
        try {
            if (id != null && books != null) {
                boolean removed = books.removeIf(book -> book != null && id.equals(book.getId()));
                if (removed) {
                    System.out.println("Deleted book with ID: " + id);
                }
            }
        } catch (Exception e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }
}