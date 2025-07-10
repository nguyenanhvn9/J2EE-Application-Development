package com.example.BaitapJ2EE.QuanLySach;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import com.example.BaitapJ2EE.QuanLySach.ResourceNotFoundException;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final String API_URL = "https://gutendex.com/books";

    @Cacheable("books")
    public List<Book> fetchBooks() {
        RestTemplate restTemplate = new RestTemplate();
        GutendexResponse response = restTemplate.getForObject(API_URL, GutendexResponse.class);
        books.clear();
        if (response != null && response.getResults() != null) {
            for (BookDTO dto : response.getResults()) {
                String authors = (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) ? dto.getAuthors().get(0).getName() : "";
                String cover = (dto.getFormats() != null) ? dto.getFormats().getImageJpeg() : null;
                books.add(new Book(
                    dto.getId(),
                    dto.getTitle(),
                    authors,
                    dto.getDescription(),
                    cover
                ));
            }
        }
        return books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public boolean addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty() || book.getAuthors() == null || book.getAuthors().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title and authors must not be null or empty.");
        }
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                throw new IllegalArgumentException("Book with this ID already exists.");
            }
        }
        books.add(book);
        return true;
    }

    public Optional<Book> updateBook(Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty() || book.getAuthors() == null || book.getAuthors().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title and authors must not be null or empty.");
        }
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        if (keyword == null) return result;
        String lower = keyword.toLowerCase();
        for (Book b : books) {
            if ((b.getTitle() != null && b.getTitle().toLowerCase().contains(lower)) ||
                (b.getAuthors() != null && b.getAuthors().toLowerCase().contains(lower))) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> getBooks(String author, Integer page, Integer size) {
        List<Book> filtered = books;
        if (author != null && !author.trim().isEmpty()) {
            filtered = books.stream()
                .filter(b -> b.getAuthors() != null && b.getAuthors().toLowerCase().contains(author.toLowerCase()))
                .toList();
        }
        if (page != null && size != null && page >= 0 && size > 0) {
            int skip = page * size;
            filtered = filtered.stream().skip(skip).limit(size).toList();
        }
        return filtered;
    }

    public Book getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }

    // DTO cho response tổng thể của Gutendex
    public static class GutendexResponse {
        private List<BookDTO> results;
        public List<BookDTO> getResults() { return results; }
        public void setResults(List<BookDTO> results) { this.results = results; }
    }
} 