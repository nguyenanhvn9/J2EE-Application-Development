package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        Stream<Book> stream = books.stream();

        // Lọc theo tên tác giả nếu có
        if (author != null && !author.isEmpty()) {
            stream = stream.filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()));
        }

        // Phân trang nếu có
        if (page != null && size != null) {
            int skip = page * size;
            stream = stream.skip(skip).limit(size);
        }

        return stream.collect(Collectors.toList());
    }

    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found."));
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

    @Cacheable("books")
    public void fetchBooks() {
        if (!books.isEmpty()) return;

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
