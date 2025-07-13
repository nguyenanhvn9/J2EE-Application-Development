package com.hutech.__truonghoanghuy.service;

import com.hutech.__truonghoanghuy.dto.BookDTO;
import com.hutech.__truonghoanghuy.dto.BookResponseDTO;
import com.hutech.__truonghoanghuy.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public void fetchBooks() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://gutendex.com/books";
        BookResponseDTO response = restTemplate.getForObject(url, BookResponseDTO.class);

        if (response != null && response.results != null) {
            for (BookDTO dto : response.results) {
                String authorName = (dto.authors != null && !dto.authors.isEmpty()) ? dto.authors.get(0).name : "Unknown";
                Book book = new Book(dto.id, dto.title, authorName);
                books.add(book);
            }
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public boolean addBook(Book book) {
        // Kiểm tra dữ liệu đầu vào
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title must not be empty.");
        }
        if (book.getAuthorName() == null || book.getAuthorName().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author must not be empty.");
        }
        // Kiểm tra trùng ID
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                throw new IllegalArgumentException("Book with this ID already exists.");
            }
        }
        books.add(book);
        return true;
    }

    public Optional<Book> updateBook(Book book) {
        // Kiểm tra dữ liệu đầu vào
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title must not be empty.");
        }
        if (book.getAuthorName() == null || book.getAuthorName().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author must not be empty.");
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
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                books.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        if (keyword == null) return result;
        String lowerKeyword = keyword.toLowerCase();
        for (Book book : books) {
            if ((book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerKeyword)) ||
                (book.getAuthorName() != null && book.getAuthorName().toLowerCase().contains(lowerKeyword))) {
                result.add(book);
            }
        }
        return result;
    }
} 