package com.hutech.__lehoanganh.demo.service;

import com.hutech.__lehoanganh.demo.dto.BookDto;
import com.hutech.__lehoanganh.demo.dto.GutendexResponse;
import com.hutech.__lehoanganh.demo.exception.ResourceNotFoundException;
import com.hutech.__lehoanganh.demo.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://gutendex.com/books";

    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        java.util.stream.Stream<Book> stream = books.stream();
        if (author != null && !author.trim().isEmpty()) {
            String lower = author.toLowerCase();
            stream = stream.filter(b -> b.getAuthors() != null && b.getAuthors().stream().anyMatch(a -> a != null && a.toLowerCase().contains(lower)));
        }
        if (page != null && size != null && page >= 0 && size > 0) {
            stream = stream.skip((long) page * size).limit(size);
        }
        return stream.collect(java.util.stream.Collectors.toList());
    }

    public Book getBookById(Integer id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Cacheable("books")
    public void fetch() {
        GutendexResponse response = restTemplate.getForObject(API_URL, GutendexResponse.class);
        if (response != null && response.getResults() != null) {
            books.clear();
            for (BookDto dto : response.getResults()) {
                List<String> authorNames = dto.getAuthors() != null ?
                    dto.getAuthors().stream().map(BookDto.AuthorDto::getName).collect(Collectors.toList()) :
                    new ArrayList<>();
                String coverUrl = dto.getFormats() != null ? dto.getFormats().getImageJpeg() : null;
                books.add(new Book(dto.getId(), dto.getTitle(), authorNames, coverUrl));
            }
        }
    }

    public List<Book> getBooks() {
        fetch(); // Luôn fetch dữ liệu mới nhất từ API
        return books;
    }

    public boolean addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty() ||
            book.getAuthors() == null || book.getAuthors().isEmpty() ||
            book.getAuthors().stream().anyMatch(a -> a == null || a.trim().isEmpty())) {
            throw new IllegalArgumentException("Book title and authors must not be null or empty.");
        }
        if (books.stream().anyMatch(b -> b.getId().equals(book.getId()))) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        return books.add(book);
    }

    public java.util.Optional<Book> updateBook(Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty() ||
            book.getAuthors() == null || book.getAuthors().isEmpty() ||
            book.getAuthors().stream().anyMatch(a -> a == null || a.trim().isEmpty())) {
            throw new IllegalArgumentException("Book title and authors must not be null or empty.");
        }
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(book.getId())) {
                books.set(i, book);
                return java.util.Optional.of(book);
            }
        }
        return java.util.Optional.empty();
    }

    public boolean deleteBook(Integer id) {
        return books.removeIf(b -> b.getId().equals(id));
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return new ArrayList<>();
        String lower = keyword.toLowerCase();
        return books.stream().filter(b ->
            (b.getTitle() != null && b.getTitle().toLowerCase().contains(lower)) ||
            (b.getAuthors() != null && b.getAuthors().stream().anyMatch(a -> a != null && a.toLowerCase().contains(lower)))
        ).collect(java.util.stream.Collectors.toList());
    }
} 