package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.exception.ResourceNotFoundException;
import com.example.bookservice.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final String API_URL = "https://gutendex.com/books";
    private final RestTemplate restTemplate = new RestTemplate();
    private final List<Book> books = new ArrayList<>();

    @Cacheable("books")
    public List<Book> fetchBooks() {
        if (books.isEmpty()) {
            BookResponseDTO response = restTemplate.getForObject(API_URL, BookResponseDTO.class);
            if (response != null && response.results != null) {
                for (BookDTO dto : response.results) {
                    String authorName = dto.authors != null && !dto.authors.isEmpty()
                            ? dto.authors.get(0).name
                            : "Unknown Author";
                    books.add(new Book(dto.id, dto.title, authorName));
                }
            }
        }
        return books;
    }

    // Thêm sách, kiểm tra trùng ID và validate dữ liệu
    public boolean addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty())
            throw new IllegalArgumentException("Title must not be empty.");
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty())
            throw new IllegalArgumentException("Author must not be empty.");
        if (books.stream().anyMatch(b -> b.getId() == book.getId()))
            throw new IllegalArgumentException("Book with this ID already exists.");
        books.add(book);
        return true;
    }

    // Cập nhật sách, trả về Optional<Book>
    public Optional<Book> updateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty())
            throw new IllegalArgumentException("Title must not be empty.");
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty())
            throw new IllegalArgumentException("Author must not be empty.");
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    // Xóa sách, trả về boolean
    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null) return Collections.emptyList();
        String lower = keyword.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if ((b.getTitle() != null && b.getTitle().toLowerCase().contains(lower)) ||
                (b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lower))) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> getBooks(String author, Integer page, Integer size) {
        if (books.isEmpty()) {
            fetchBooks();
        }
        List<Book> filtered = books;
        if (author != null && !author.isEmpty()) {
            filtered = books.stream()
                    .filter(b -> b.getAuthor() != null && b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .toList();
        }
        if (page != null && size != null && page >= 0 && size > 0) {
            int fromIndex = page * size;
            int toIndex = Math.min(fromIndex + size, filtered.size());
            if (fromIndex >= filtered.size()) return List.of();
            return filtered.subList(fromIndex, toIndex);
        }
        return filtered;
    }

    public Book getBookById(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }
}
