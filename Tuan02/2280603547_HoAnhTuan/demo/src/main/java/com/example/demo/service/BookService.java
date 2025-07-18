package com.example.demo.service;

import com.example.demo.dto.AuthorDto;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.GutendexResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final List<Book> books = Collections.synchronizedList(new ArrayList<>());
    private static final String API_URL = "https://gutendex.com/books";
    private final RestTemplate restTemplate;

    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "books")
    public List<Book> getAllBooks(String author, int page, int size) {
        if (books.isEmpty()) {
            logger.info("Book list is empty, fetching from API");
            fetchBooksFromApi();
        }

        List<Book> filteredBooks = books;
        if (author != null && !author.trim().isEmpty()) {
            String lowerAuthor = author.toLowerCase();
            filteredBooks = books.stream()
                    .filter(book -> book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerAuthor))
                    .collect(Collectors.toList());
        }

        int start = page * size;
        int end = Math.min(start + size, filteredBooks.size());
        if (start >= filteredBooks.size()) {
            return new ArrayList<>();
        }

        return filteredBooks.subList(start, end);
    }

    public Optional<Book> getBookById(Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        if (book.isEmpty()) {
            throw new ResourceNotFoundException("Book with ID " + id + " not found");
        }
        return book;
    }

    public boolean addBook(Book book) {
        validateBook(book);
        synchronized (books) {
            if (books.stream().anyMatch(b -> b.getId().equals(book.getId()))) {
                throw new IllegalArgumentException("Book with this ID already exists.");
            }
            return books.add(book);
        }
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        validateBook(updatedBook);
        synchronized (books) {
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                if (book.getId().equals(id)) {
                    updatedBook.setId(id);
                    books.set(i, updatedBook);
                    return Optional.of(updatedBook);
                }
            }
        }
        throw new ResourceNotFoundException("Book with ID " + id + " not found");
    }

    public boolean deleteBook(Long id) {
        synchronized (books) {
            Optional<Book> book = books.stream()
                    .filter(b -> b.getId().equals(id))
                    .findFirst();
            if (book.isPresent()) {
                return books.removeIf(b -> b.getId().equals(id));
            }
            throw new ResourceNotFoundException("Book with ID " + id + " not found");
        }
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(books);
        }
        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(book -> (book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerKeyword))
                        || (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerKeyword)))
                .collect(Collectors.toList());
    }

    private void fetchBooksFromApi() {
        try {
            GutendexResponseDto response = restTemplate.getForObject(API_URL, GutendexResponseDto.class);
            if (response != null && response.getResults() != null) {
                for (BookDto dto : response.getResults()) {
                    String author = (dto.getAuthors() != null && !dto.getAuthors().isEmpty())
                            ? dto.getAuthors().stream()
                            .map(AuthorDto::getName)
                            .collect(Collectors.joining(", "))
                            : "Unknown";
                    long id = books.size() + 1; // Simple ID generation
                    books.add(new Book(id, dto.getTitle(), author));
                }
                logger.info("Fetched and cached {} books from Gutendex API", books.size());
            } else {
                logger.error("Failed to fetch books: Empty or null response");
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error fetching books: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error fetching books: {}", e.getMessage());
        }
    }

    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (book.getId() == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }
    }
}