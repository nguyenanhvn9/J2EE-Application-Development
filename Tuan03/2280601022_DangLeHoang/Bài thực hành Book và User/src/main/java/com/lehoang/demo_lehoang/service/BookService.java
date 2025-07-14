package com.lehoang.demo_lehoang.service;

import com.lehoang.demo_lehoang.model.Book;
import com.lehoang.demo_lehoang.dto.BookGutenDTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;
    public List<Book> getAllBooks() {
        return books;
    }
    public boolean addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }
        if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        book.setId(nextId++);
        books.add(book);
        return true;
    }
    public Optional<Book> getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst();
    }
    public Optional<Book> updateBook(Book updatedBook) {
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }
        return books.stream()
                .filter(book -> book.getId() == updatedBook.getId())
                .findFirst()
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    return book;
                });
    }
    public boolean deleteBook(Long id) {
        return books.removeIf(book -> book.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null) return List.of();
        String lower = keyword.toLowerCase();
        return books.stream()
                .filter(book -> (book.getTitle() != null && book.getTitle().toLowerCase().contains(lower))
                        || (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lower)))
                .collect(Collectors.toList());
    }

    public void fetchAndAddBooksFromGutendex() {
        String url = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.getForObject(url, GutendexResponse.class);
        if (response != null && response.results != null) {
            response.results.stream().limit(5).forEach(dto -> {
                Book book = new Book();
                book.setId(nextId++);
                book.setTitle(dto.getTitle());
                String author = (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) ? dto.getAuthors().get(0).getName() : "Unknown";
                book.setAuthor(author);
                books.add(book);
            });
        }
    }

    public List<Book> getBooksByPage(int page, int size) {
        int fromIndex = Math.max(0, (page - 1) * size);
        int toIndex = Math.min(books.size(), fromIndex + size);
        if (fromIndex > toIndex) return List.of();
        return books.subList(fromIndex, toIndex);
    }

    public int getTotalPages(int size) {
        return (int) Math.ceil((double) books.size() / size);
    }

    // DTO cho response tổng thể của Gutendex
    public static class GutendexResponse {
        @JsonProperty("results")
        public List<BookGutenDTO> results;
    }
}
