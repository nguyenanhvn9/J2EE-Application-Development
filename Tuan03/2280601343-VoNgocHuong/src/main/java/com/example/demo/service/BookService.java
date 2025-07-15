package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.GutendexResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static final String API_URL = "https://gutendex.com/books";
    private final List<Book> books = new ArrayList<>();

    public List<Book> fetchBooks() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            GutendexResponseDto response = restTemplate.getForObject(API_URL, GutendexResponseDto.class);
            List<Book> books = new ArrayList<>();
            if (response != null && response.getResults() != null) {
                books = response.getResults().stream().map(this::convertToBook).collect(Collectors.toList());
            }
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void loadBooksFromApi() {
        List<Book> apiBooks = fetchBooks();
        books.clear();
        books.addAll(apiBooks);
    }

    private Book convertToBook(BookDto dto) {
        List<String> authorNames = new ArrayList<>();
        if (dto.getAuthors() != null) {
            for (BookDto.AuthorDto author : dto.getAuthors()) {
                authorNames.add(author.getName());
            }
        }
        return new Book(dto.getId(), dto.getTitle(), authorNames, dto.getDownload_count());
    }

    public boolean addBook(Book book) {
        if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        books.add(book);
        return true;
    }

    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }

    public List<Book> getAllBooks() {
        if (books.isEmpty()) {
            loadBooksFromApi();
        }
        return new ArrayList<>(books);
    }
} 