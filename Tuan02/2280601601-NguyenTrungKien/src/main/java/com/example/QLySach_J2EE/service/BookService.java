package com.example.QLySach_J2EE.service;

import com.example.QLySach_J2EE.dto.GutendexBookDto;
import com.example.QLySach_J2EE.dto.GutendexResponseDto;
import com.example.QLySach_J2EE.exception.ResourceNotFoundException;
import com.example.QLySach_J2EE.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;

    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        lazyLoadBooks();
        Stream<Book> stream = books.stream();
        if (author != null && !author.isEmpty()) {
            stream = stream.filter(b -> b.getAuthor() != null && b.getAuthor().toLowerCase().contains(author.toLowerCase()));
        }
        if (page != null && size != null) {
            stream = stream.skip((long) page * size).limit(size);
        }
        return stream.collect(Collectors.toList());
    }

    public Book getBookById(int id) {
        lazyLoadBooks();
        return books.stream().filter(book -> book.getId() == id).findFirst().orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public boolean addBook(Book book) {
        validateBook(book);
        lazyLoadBooks();
        if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        books.add(book);
        return true;
    }

    public Optional<Book> updateBook(int id, Book updatedBook) {
        validateBook(updatedBook);
        lazyLoadBooks();
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
        lazyLoadBooks();
        return books.removeIf(book -> book.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        lazyLoadBooks();
        String lower = keyword.toLowerCase();
        return books.stream()
                .filter(b -> (b.getTitle() != null && b.getTitle().toLowerCase().contains(lower)) ||
                        (b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lower)))
                .collect(Collectors.toList());
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title must not be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author must not be empty");
        }
    }

    @Cacheable("books")
    public void lazyLoadBooks() {
        if (!books.isEmpty()) return;
        String url = "https://gutendex.com/books";
        ResponseEntity<GutendexResponseDto> response = restTemplate.getForEntity(url, GutendexResponseDto.class);
        GutendexResponseDto data = response.getBody();
        if (data != null && data.getResults() != null) {
            for (GutendexBookDto dto : data.getResults()) {
                String author = (dto.getAuthors() != null && dto.getAuthors().length > 0) ? dto.getAuthors()[0].getName() : "Unknown";
                books.add(new Book(dto.getId(), dto.getTitle(), author));
            }
        }
    }
}