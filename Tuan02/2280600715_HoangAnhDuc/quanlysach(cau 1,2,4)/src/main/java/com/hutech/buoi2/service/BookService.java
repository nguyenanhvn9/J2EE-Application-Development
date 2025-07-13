package com.hutech.buoi2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.hutech.buoi2.dto.BookDTO;
import com.hutech.buoi2.exception.ResourceNotFoundException;
import com.hutech.buoi2.model.Book;

import jakarta.annotation.PostConstruct;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();

    // ✅ Tự động gọi API khi khởi động và lưu vào books
    @PostConstruct
    public void initBooksFromPublicAPI() {
        List<BookDTO> publicBooks = fetchBooksFromAPI();

        for (BookDTO dto : publicBooks) {
            Book book = new Book(dto.getId(), dto.getTitle(), dto.getAuthor());

            // Tránh trùng ID
            if (books.stream().noneMatch(b -> b.getId() == book.getId())) {
                books.add(book);
            }
        }

        System.out.println("✅ Loaded " + books.size() + " books from Public API at startup.");
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found."));
    }

    public boolean addBook(Book book) {
        if (book == null || isInvalid(book)) {
            throw new IllegalArgumentException("Title và Author không được để trống.");
        }

        boolean exists = books.stream().anyMatch(b -> b.getId() == book.getId());
        if (exists) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }

        books.add(book);
        return true;
    }

    public Optional<Book> updateBook(int id, Book updatedBook) {
        if (updatedBook == null || isInvalid(updatedBook)) {
            throw new IllegalArgumentException("Title và Author không được để trống.");
        }

        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    return book;
                });
    }

    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String lowerKeyword = keyword.toLowerCase();

        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerKeyword)
                || book.getAuthor().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public List<Book> getFilteredBooks(String author, int page, int size) {
        return books.stream()
                .filter(book -> author == null || book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    // ✅ Gọi API Gutendex và ánh xạ DTO
    @Cacheable("publicBooks")
    public List<BookDTO> fetchBooksFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://gutendex.com/books";
        JsonNode root = restTemplate.getForObject(apiUrl, JsonNode.class);

        List<BookDTO> bookDTOs = new ArrayList<>();
        if (root != null && root.has("results")) {
            for (JsonNode bookNode : root.get("results")) {
                int id = bookNode.get("id").asInt();
                String title = bookNode.get("title").asText();

                List<String> authorNames = new ArrayList<>();
                for (JsonNode authorNode : bookNode.get("authors")) {
                    authorNames.add(authorNode.get("name").asText());
                }

                String author = String.join(", ", authorNames);
                bookDTOs.add(new BookDTO(id, title, author));
            }
        }

        return bookDTOs;
    }

    private boolean isInvalid(Book book) {
        return book.getTitle() == null || book.getTitle().trim().isEmpty()
                || book.getAuthor() == null || book.getAuthor().trim().isEmpty();
    }
}
