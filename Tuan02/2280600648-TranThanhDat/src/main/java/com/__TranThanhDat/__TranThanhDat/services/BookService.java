package com.__TranThanhDat.__TranThanhDat.services;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.__TranThanhDat.__TranThanhDat.dtos.BookApiResponse;
import com.__TranThanhDat.__TranThanhDat.dtos.BookDTO;
import com.__TranThanhDat.__TranThanhDat.exceptions.ResourceNotFoundException;
import com.__TranThanhDat.__TranThanhDat.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public void fetchBooks() {
        String url = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();

        BookApiResponse response = restTemplate.getForObject(url, BookApiResponse.class);
        if (response != null && response.getResults() != null) {
            for (BookDTO dto : response.getResults()) {
                String authorName = dto.getAuthors().isEmpty() ? "Unknown" : dto.getAuthors().get(0).getName();
                Book book = new Book(dto.getId(), dto.getTitle(), authorName);
                books.add(book);
            }
        }
    }

        public List<Book> getBooks(String author, int page, int size) {
            if (books.isEmpty()) {
                fetchBooks();
            }
            Stream<Book> stream = books.stream();

            if (author != null && !author.isBlank()) {
                String lowerAuthor = author.toLowerCase();
                stream = stream.filter(b -> b.getAuthor().toLowerCase().contains(lowerAuthor));
            }

            return stream
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
        }


    public boolean addBook(Book book) {
    if (book == null || book.getId() == 0) {
        throw new IllegalArgumentException("Book is invalid.");
    }

    // Kiểm tra ID trùng
    for (Book b : books) {
        if (b.getId() == book.getId()) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
    }

    // Kiểm tra title & author
    if (book.getTitle() == null || book.getTitle().isBlank()
     || book.getAuthor() == null || book.getAuthor().isBlank()) {
        throw new IllegalArgumentException("Title and author must not be empty.");
    }

    books.add(book);
    return true;
}

    public Optional<Book> updateBook(int id, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                if (updatedBook.getTitle() == null || updatedBook.getTitle().isBlank()
                || updatedBook.getAuthor() == null || updatedBook.getAuthor().isBlank()) {
                    throw new IllegalArgumentException("Title and author must not be empty.");
                }

                books.set(i, new Book(id, updatedBook.getTitle(), updatedBook.getAuthor()));
                return Optional.of(books.get(i));
            }
        }
        return Optional.empty();
    }
    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.isBlank()) return new ArrayList<>();

        String lower = keyword.toLowerCase();
        return books.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(lower) || b.getAuthor().toLowerCase().contains(lower))
            .collect(Collectors.toList());
    }

    public Book getBookById(int id) {
    return books.stream()
            .filter(b -> b.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
}

}
