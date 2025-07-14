package com.hutech.__truonghoanghuy.service;

import com.hutech.__truonghoanghuy.dto.BookDTO;
import com.hutech.__truonghoanghuy.dto.BookResponseDTO;
import com.hutech.__truonghoanghuy.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public void fetchBooks() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://gutendex.com/books";
        BookResponseDTO response = restTemplate.getForObject(url, BookResponseDTO.class);

        if (response != null && response.results != null) {
            for (BookDTO dto : response.results) {
                List<String> authorNames = (dto.authors != null)
                    ? dto.authors.stream().map(a -> a.name).collect(Collectors.toList())
                    : new ArrayList<>();
                Book book = new Book(dto.id, dto.title, authorNames);
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
        if (book.getAuthorNames() == null || book.getAuthorNames().isEmpty() ||
            book.getAuthorNames().stream().anyMatch(a -> a == null || a.trim().isEmpty())) {
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
        if (book.getAuthorNames() == null || book.getAuthorNames().isEmpty() ||
            book.getAuthorNames().stream().anyMatch(a -> a == null || a.trim().isEmpty())) {
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
                (book.getAuthorNames() != null && book.getAuthorNames().stream().anyMatch(a -> a != null && a.toLowerCase().contains(lowerKeyword)))) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        // Lọc theo author nếu có
        Stream<Book> stream = books.stream();
        if (author != null && !author.trim().isEmpty()) {
            String lowerAuthor = author.toLowerCase();
            stream = stream.filter(b -> b.getAuthorNames() != null &&
                b.getAuthorNames().stream().anyMatch(a -> a != null && a.toLowerCase().contains(lowerAuthor)));
        }
        // Phân trang nếu có
        if (page != null && size != null && page >= 0 && size > 0) {
            stream = stream.skip((long) page * size).limit(size);
        }
        return stream.collect(Collectors.toList());
    }
} 