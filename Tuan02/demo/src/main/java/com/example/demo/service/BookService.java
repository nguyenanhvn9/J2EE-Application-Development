package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    public List<Book> getAllBooks() {
        return books;
    }
    public Book getBookById(int id) {
        return books.stream().filter(book -> book.getId() ==
                id).findFirst().orElse(null);
    }
    public void addBook(Book book) {
        books.add(book);
    }
    public void updateBook(int id, Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }

    public void fetchBooks() {
        if (!books.isEmpty()) return; // tránh gọi nhiều lần

        String url = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ResultDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResultDTO>() {}
        );

        List<BookDTO> bookDTOs = response.getBody().getResults();
        for (BookDTO dto : bookDTOs) {
            String authorNames = dto.getAuthors().stream()
                    .map(AuthorDTO::getName)
                    .collect(Collectors.joining(", "));

            books.add(new Book(dto.getId(), dto.getTitle(), authorNames));
        }
    }
}