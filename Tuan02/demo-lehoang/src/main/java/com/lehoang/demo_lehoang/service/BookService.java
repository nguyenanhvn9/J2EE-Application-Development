package com.lehoang.demo_lehoang.service;

import com.lehoang.demo_lehoang.model.Book;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import com.lehoang.demo_lehoang.dto.GutendexResponse;
import com.lehoang.demo_lehoang.dto.BookDto;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    public List<Book> getAllBooks() {
        return books;
    }
    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        // Lọc theo author nếu có
        Stream<Book> stream = books.stream();
        if (author != null && !author.trim().isEmpty()) {
            String lowerAuthor = author.toLowerCase();
            stream = stream.filter(book -> book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerAuthor));
        }
        // Phân trang
        if (page != null && size != null && page >= 0 && size > 0) {
            stream = stream.skip((long) page * size).limit(size);
        }
        return stream.toList();
    }
    public Book getBookById(int id) {
        return books.stream().filter(book -> book.getId() ==
                id).findFirst().orElse(null);
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
        books.add(book);
        return true;
    }
    public Optional<Book> updateBook(int id, Book updatedBook) {
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }
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

    public void fetchBooksFromApi() {
        String url = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();
        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);
        if (response != null && response.results != null) {
            for (BookDto dto : response.results) {
                String author = (dto.authors != null && dto.authors.length > 0) ? dto.authors[0].name : "Unknown";
                Book book = new Book(dto.id, dto.title, author);
                books.add(book);
            }
        }
    }

    @PostConstruct
    public void init() {
        fetchBooksFromApi();
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null) return List.of();
        String lowerKeyword = keyword.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if ((book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerKeyword)) ||
                (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerKeyword))) {
                result.add(book);
            }
        }
        return result;
    }
}
