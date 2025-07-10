package com.example.BookService.Service;

import com.example.BookService.DTO.BookDTO;
import com.example.BookService.DTO.BookResponse;
import com.example.BookService.DTO.GutendexResponse;
import com.example.BookService.Exception.ResourceNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {

    private final List<BookResponse> books = new ArrayList<>();


    @Cacheable("booksCache")
    public void fetchBooksFromApi() {
        if (!books.isEmpty()) return;

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://gutendex.com/books";

        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);

        if (response != null) {
            for (BookDTO dto : response.getResults()) {
                BookResponse book = new BookResponse(dto.getId(), dto.getTitle(), dto.getAuthor());
                books.add(book);
            }
        }
    }

    public List<BookResponse> getBooks(String author, int page, int size) {
        Stream<BookResponse> stream = books.stream();

        if (author != null && !author.trim().isEmpty()) {
            String lowerAuthor = author.toLowerCase();
            stream = stream.filter(book -> book.getAuthor().toLowerCase().contains(lowerAuthor));
        }

        return stream
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public BookResponse getBookById(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }

    public boolean addBook(BookResponse book) {
        boolean exists = books.stream().anyMatch(b -> b.getId() == book.getId());
        if (exists) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }

        if (book.getTitle() == null || book.getTitle().trim().isEmpty()
                || book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title and author must not be empty.");
        }

        books.add(book);
        return true;
    }

    public Optional<BookResponse> updateBook(int id, BookResponse updatedBook) {
        for (BookResponse b : books) {
            if (b.getId() == id) {
                if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()
                        || updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
                    throw new IllegalArgumentException("Title and author cannot be empty.");
                }

                b.setTitle(updatedBook.getTitle());
                b.setAuthor(updatedBook.getAuthor());
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }

    public List<BookResponse> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String lowerKeyword = keyword.toLowerCase();

        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lowerKeyword)
                        || b.getAuthor().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
}
