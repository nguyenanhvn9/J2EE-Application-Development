package com.hutech.buoi3book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.hutech.buoi3book.dto.BookDTO;
import com.hutech.buoi3book.model.Book;

import jakarta.annotation.PostConstruct;

@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();
    private long nextId = 1; // ✅ ID sẽ tự tăng để thêm sách

    // ✅ Gọi API và load 5 sách đầu tiên khi start
    @PostConstruct
    public void initBooksFromPublicAPI() {
        List<BookDTO> publicBooks = fetchBooksFromAPI().stream()
                .limit(5) // ✅ Chỉ lấy 5 sách
                .collect(Collectors.toList());

        for (BookDTO dto : publicBooks) {
            Book book = new Book(dto.getId(), dto.getTitle(), dto.getAuthor());

            if (books.stream().noneMatch(b -> b.getId() == book.getId())) {
                books.add(book);
            }

            // ✅ Đảm bảo nextId không trùng ID đã có
            nextId = Math.max(nextId, book.getId() + 1);
        }

        System.out.println("✅ Loaded " + books.size() + " books from Public API at startup.");
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return books.stream()
                .filter(book -> id.equals(book.getId()))
                .findFirst();
    }

    public boolean addBook(Book book) {
        if (book == null || isInvalid(book)) {
            throw new IllegalArgumentException("Title và Author không được để trống.");
        }

        // ✅ Tự sinh ID mới
        book.setId(nextId++);
        books.add(book);
        return true;
    }

    public boolean updateBook(Book updatedBook) {
        if (updatedBook == null || updatedBook.getId() == null) {
            return false;
        }

        return books.stream()
                .filter(book -> updatedBook.getId().equals(book.getId()))
                .findFirst()
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    return true;
                }).orElse(false);
    }

    public boolean deleteBook(Long id) {
        if (id == null) {
            return false;
        }
        return books.removeIf(book -> id.equals(book.getId()));
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

    @Cacheable("publicBooks")
    public List<BookDTO> fetchBooksFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://gutendex.com/books";
        JsonNode root;

        try {
            root = restTemplate.getForObject(apiUrl, JsonNode.class);
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi gọi API: " + e.getMessage());
            return List.of();
        }

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
