package com.lehoang.demo_lehoang.service;

import com.lehoang.demo_lehoang.model.Book;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import javax.annotation.PostConstruct;

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
}

// DTO classes for API response
class GutendexResponse {
    public int count;
    public String next;
    public String previous;
    public BookDto[] results;
}
class BookDto {
    public int id;
    public String title;
    public AuthorDto[] authors;
}
class AuthorDto {
    public String name;
    @JsonProperty("birth_year")
    public Integer birthYear;
    @JsonProperty("death_year")
    public Integer deathYear;
}
