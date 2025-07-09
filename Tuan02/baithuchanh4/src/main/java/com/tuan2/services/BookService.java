package com.tuan2.services;

import com.tuan2.ExceptionHandling.ResourceNotFoundException;
import com.tuan2.dto.BookDTO;
import com.tuan2.dto.GutendexResponse;
import com.tuan2.models.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public void fetchBooksFromGutenberg() {
        String apiUrl = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();

        GutendexResponse response = restTemplate.getForObject(apiUrl, GutendexResponse.class);

        if (response != null && response.getResults() != null) {
            for (BookDTO dto : response.getResults()) {
                String authorName = (dto.getAuthors() != null && !dto.getAuthors().isEmpty())
                        ? dto.getAuthors().get(0).getName()
                        : "Unknown";

                Book book = new Book((int) idCounter.getAndIncrement(), dto.getTitle(), authorName);
                books.add(book);
            }
        }
    }

    public List<Book> getAllBooks(String author, int page, int size) {
        if (books.isEmpty())
            fetchBooksFromGutenberg();
        return books.stream()
                .filter(book -> author == null || book.getAuthor().equalsIgnoreCase(author))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    public Book getBookById(Long id) {
        if (books.isEmpty())
            fetchBooksFromGutenberg();
        return books.stream()
                .filter(book -> book.getId() == (id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }
}
