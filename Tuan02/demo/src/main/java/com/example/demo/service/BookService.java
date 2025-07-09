package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean addBook(Book book) {
        validateBook(book);

        boolean exists = books.stream().anyMatch(b -> b.getId() == book.getId());
        if (exists) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }

        books.add(book);
        return true;
    }

    public Optional<Book> updateBook(int id, Book updatedBook) {
        validateBook(updatedBook);

        for (Book book : books) {
            if (book.getId() == id) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(lowerKeyword) ||
                                book.getAuthor().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public void fetchBooks() {
        if (!books.isEmpty()) return; // tránh gọi nhiều lần

        String url = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ResultDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResultDTO>() {}
        );

        List<BookDTO> bookDTOs = response.getBody().getResults();
        for (BookDTO dto : bookDTOs) {
            String authorNames = dto.getAuthors().stream()
                    .map(AuthorDTO::getName)
                    .collect(Collectors.joining(", "));

            books.add(new Book(dto.getId(), dto.getTitle(), authorNames));
        }
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty() ||
                book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and author must not be empty.");
        }
    }
}
