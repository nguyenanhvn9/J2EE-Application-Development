package com.example.service;

import com.example.dto.GutendexBookDTO;
import com.example.dto.GutendexResponse;
import com.example.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    public List<Book> fetchBooks() {
        if (books.isEmpty()) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://gutendex.com/books";

            GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);
            if (response != null && response.getResults() != null) {
                for (GutendexBookDTO dto : response.getResults()) {
                    String author = dto.getAuthors().isEmpty() ? "Unknown" : dto.getAuthors().get(0).name;
                    Book book = new Book(dto.getId(), dto.getTitle(), author);
                    books.add(book);
                }
            }
        }
        return books;
    }
}
