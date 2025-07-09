package com.tuan2.controllers;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tuan2.dto.BookDTO;
import com.tuan2.dto.GutendexResponse;
import com.tuan2.models.Book;
import com.tuan2.services.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final RestTemplate restTemplate;
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.restTemplate = new RestTemplate();
        this.bookService = bookService;
    }

    @GetMapping("/fetch-books")
    public String fetchBooksFromApi() {
        String url = "https://gutendex.com/books";
        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);

        if (response != null && response.getResults() != null) {
            for (BookDTO dto : response.getResults()) {
                String authorName = dto.getAuthors().isEmpty() ? "Unknown" : dto.getAuthors().get(0).getName();
                Book book = new Book(dto.getId(), dto.getTitle(), authorName);
                bookService.addBook(book);
            }
        }

        return "Fetched and stored " + bookService.getBooks().size() + " books.";
    }

    @GetMapping()
    public ResponseEntity<java.util.List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }
}