package com.example.project_01.Bai1_2_4_QuanLySach.service;

import com.example.project_01.Bai1_2_4_QuanLySach.model.Book;
import com.example.project_01.Bai1_2_4_QuanLySach.dto.GutendexResponseDTO;
import com.example.project_01.Bai1_2_4_QuanLySach.dto.GutendexBookDTO;

import com.example.project_01.Bai1_2_4_QuanLySach.exception.ResourceNotFoundException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private  List<Book> books = new ArrayList<>();

    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        // Lọc theo author nếu có
        Stream<Book> stream = books.stream();
        if (author != null && !author.trim().isEmpty()) {
            String lowerAuthor = author.toLowerCase();
            stream = stream.filter(book -> book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerAuthor));
        }
        // Phân trang nếu có
        if (page != null && size != null && page >= 0 && size > 0) {
            stream = stream.skip((long) page * size).limit(size);
        }
        return stream.collect(Collectors.toList());
    }

    public Book getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public boolean addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty() ||
            book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and author must not be empty or null.");
        }
        if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        books.add(book);
        return true;
    }

    public Optional<Book> updateBook(int id, Book updatedBook) {
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty() ||
            updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and author must not be empty or null.");
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

    @Cacheable("booksFromApi")
    public void fetchBooksFromApi() {
        String apiUrl = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();
        GutendexResponseDTO response = restTemplate.getForObject(apiUrl, GutendexResponseDTO.class);
        if (response != null && response.getResults() != null) {
            for (GutendexBookDTO dto : response.getResults()) {
                String authorName = (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) ? dto.getAuthors().get(0).getName() : "Unknown";
                Book book = new Book(dto.getId(), dto.getTitle(), authorName);
                // Tránh thêm trùng id
                if (books.stream().noneMatch(b -> b.getId() == book.getId())) {
                    books.add(book);
                }
            }
        }
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
