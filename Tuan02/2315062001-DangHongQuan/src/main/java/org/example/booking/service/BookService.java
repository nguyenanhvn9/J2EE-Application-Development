package org.example.booking.service;

import org.example.booking.Response.BookResponse;
import org.example.booking.dto.AuthorDTO;
import org.example.booking.dto.BookDTO;
import org.example.booking.exception.ResourceNotFoundException;
import org.example.booking.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final RestTemplate restTemplate;

    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void fetchBooks() {
        String url = "https://gutendex.com/books";
        BookResponse response = restTemplate.getForObject(url, BookResponse.class);
        if (response != null && response.getResults() != null) {
            books.clear(); // Xóa dữ liệu cũ để cập nhật
            for (BookDTO dto : response.getResults()) {
                Book book = mapToBook(dto);
                books.add(book);
            }
        }
    }
    @Cacheable("books")
    public List<Book> getAllBooks() {
        if (books.isEmpty()) {
            fetchBooks();
        }
        return new ArrayList<>(books);
    }


    public Book getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    public boolean addBook(Book book) {
        if (books.stream().anyMatch(b -> b.getId().equals(book.getId()))) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        return books.add(book);
    }

    public boolean updateBook(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(book.getId())) {
                books.set(i, book);
                return true;
            }
        }
        return false;
    }

    public boolean deleteBook(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    public List<Book> searchBooks(String keyword) {
        return books.stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                                book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    private Book mapToBook(BookDTO dto) {
        // Author
        String authorName = "Unknown";
        if (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) {
            authorName = dto.getAuthors().get(0).getName();
        }

        // Language
        String language = "unknown";
        if (dto.getLanguages() != null && !dto.getLanguages().isEmpty()) {
            language = dto.getLanguages().get(0);
        }

        // Cover Image
        String imageUrl = null;
        if (dto.getFormats() != null && dto.getFormats().containsKey("image/jpeg")) {
            imageUrl = dto.getFormats().get("image/jpeg");
        }

        // Summary
        String summary = null;
        if (dto.getSummaries() != null && !dto.getSummaries().isEmpty()) {
            summary = dto.getSummaries().get(0);
        }

        return new Book(
                dto.getId(),
                dto.getTitle(),
                authorName,
                dto.getDownload_count(),
                language,
                dto.getSubjects(),
                imageUrl,
                summary
        );
    }
}
