package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import com.example.demo.model.Book;
import com.example.demo.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.example.demo.exception.ResourceNotFoundException;

@Service

public class BookService {
    private List<Book> books = new ArrayList<>();
    @Autowired
    private RestTemplate restTemplate;

    public void fetchBooksFromAPI() {
        String url = "https://gutendex.com/books";

        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);

        if (response != null && response.getResults() != null) {
            for (BookDTO dto : response.getResults()) {
                String author = dto.getAuthors().isEmpty() ? "Unknown" : dto.getAuthors().get(0).getName();
                Book book = new Book(dto.getId(), dto.getTitle(), author);
                books.add(book);
            }
        }
    }
    public List<Book> getAllBooks(String author, int page, int size) {
        Stream<Book> stream = books.stream();

        if (author != null && !author.isEmpty()) {
            stream = stream.filter(book -> 
                book.getAuthor() != null && 
                book.getAuthor().toLowerCase().contains(author.toLowerCase()));
        }

        return stream
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Book getBookById(int id) {
        return books.stream()
        .filter(book -> book.getId() == id)
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }
    public boolean addBook(Book book) {
        // Kiểm tra title và author không được null hoặc rỗng
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }

        // Kiểm tra ID đã tồn tại chưa
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                throw new IllegalArgumentException("Book with this ID already exists.");
            }
        }

        books.add(book);
        return true;
    }

    public boolean updateBook(int id, Book updatedBook) {
        // Kiểm tra dữ liệu đầu vào
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                books.set(i, updatedBook);
                return true;
            }
        }

        return false;
    }
    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
    public List<Book> searchBooks(String keyword) {
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
