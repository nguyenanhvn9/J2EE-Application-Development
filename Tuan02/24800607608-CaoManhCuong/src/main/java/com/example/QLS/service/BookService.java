package com.example.QLS.service;
import com.example.QLS.dto.BookDTO;
import com.example.QLS.dto.GutendexResponse;
import com.example.QLS.exception.ResourceNotFoundException;
import com.example.QLS.model.Book;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    public List<Book> getAllBooks(String author, int page, int size) {
        Stream<Book> stream = books.stream();

        if (author != null && !author.trim().isEmpty()) {
            stream = stream.filter(b -> b.getAuthor() != null &&
                    b.getAuthor().toLowerCase().contains(author.toLowerCase()));
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
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found."));
    }

    public void addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }
        boolean exists = books.stream().anyMatch(b -> b.getId() == book.getId());
        if (exists) {
            throw new IllegalArgumentException("Book with ID " + book.getId() + " already exists.");
        }
        books.add(book);
    }
    public Optional<Book> updateBook(int id, Book updatedBook) {
        for (Book book : books) {
            if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("Book title cannot be null or empty.");
            }
            if (updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
                throw new IllegalArgumentException("Book author cannot be null or empty.");
            }
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

    public void fetchBooksFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://gutendex.com/books";

        GutendexResponse response = restTemplate.getForObject(apiUrl, GutendexResponse.class);

        if (response != null && response.getResults() != null) {
            for (BookDTO dto : response.getResults()) {
                String authorName = (dto.getAuthors() != null && !dto.getAuthors().isEmpty())
                        ? dto.getAuthors().get(0).getName()
                        : "Unknown";

                Book book = new Book(dto.getId(), dto.getTitle(), authorName);
                books.add(book);
            }
        }
    }

    //tim kiem book
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String lowerKeyword = keyword.toLowerCase();

        return books.stream()
                .filter(book ->
                        (book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerKeyword)) ||
                                (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerKeyword))
                )
                .collect(Collectors.toList());
    }

}
