package com.hutech.cos141_demo.service;

import com.hutech.cos141_demo.exception.ResourceNotFoundException;
import com.hutech.cos141_demo.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    public boolean addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty() ||
            book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and Author cannot be empty.");
        }

        boolean exists = books.stream().anyMatch(b -> b.getId() == book.getId());
        if (exists) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }

        books.add(book);
        return true;
    }

    public Book updateBook(int id, Book updated) {
        if (updated.getTitle() == null || updated.getTitle().trim().isEmpty() ||
            updated.getAuthor() == null || updated.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and Author cannot be empty.");
        }

        for (Book b : books) {
            if (b.getId() == id) {
                b.setTitle(updated.getTitle());
                b.setAuthor(updated.getAuthor());
                return b;
            }
        }
        throw new ResourceNotFoundException("Book with ID " + id + " not found.");
    }

    public void deleteBook(int id) {
        boolean removed = books.removeIf(b -> b.getId() == id);
        if (!removed) {
            throw new ResourceNotFoundException("Book with ID " + id + " not found.");
        }
    }

    // ✅ NEW: Cacheable và lazy load
    @Cacheable("books")
    public List<Book> getAllBooks() {
        if (books.isEmpty()) {
            fetchBooksFromApi();
        }
        return books;
    }

    private void fetchBooksFromApi() {
        // 🔥 Mô phỏng gọi API public (bạn đổi sang RestTemplate)
        books.add(new Book(1, "Clean Code", "Robert C. Martin"));
        books.add(new Book(2, "The Alchemist", "Paulo Coelho"));
        books.add(new Book(3, "Thinking in Java", "Bruce Eckel"));
    }

    public Book getById(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found."));
    }

    public List<Book> searchBooks(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lowerKeyword) ||
                             b.getAuthor().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public List<Book> getBooks(String author, int page, int size) {
        Stream<Book> stream = books.stream();

        if (author != null && !author.trim().isEmpty()) {
            stream = stream.filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()));
        }

        return stream
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}
