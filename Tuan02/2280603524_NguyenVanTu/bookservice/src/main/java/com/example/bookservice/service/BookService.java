package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.dto.GutendexResponseDTO;
import com.example.bookservice.exception.ResourceNotFoundException;
import com.example.bookservice.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private final String GUTENDEX_API = "https://gutendex.com/books";

    @Cacheable("books")
    public List<Book> getBooks() {
        if (books.isEmpty()) {
            fetchBooksFromAPI();
        }
        return books;
    }

    private void fetchBooksFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        GutendexResponseDTO response = restTemplate.getForObject(GUTENDEX_API, GutendexResponseDTO.class);

        if (response != null && response.getResults() != null) {
            for (BookDTO dto : response.getResults()) {
                String author = dto.getAuthorName();
                Book book = new Book(dto.getId(), dto.getTitle(), author);
                books.add(book);
            }
        }
    }

    public boolean addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty() ||
            book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and author must not be empty.");
        }
        if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        books.add(book);
        return true;
    }

    public Optional<Book> updateBook(int id, Book updatedBook) {
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty() ||
            updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and author must not be empty.");
        }
        for (Book book : books) {
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

    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Book book : books) {
            if ((book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerKeyword)) ||
                (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerKeyword))) {
                result.add(book);
            }
        }
        return result;
    }

    public Optional<Book> getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst();
    }

    public Book getBookByIdOrThrow(int id) {
        return getBooks().stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }

    // Thêm phương thức mới cho lọc và phân trang
    public List<Book> getBooksFiltered(String author, Integer page, Integer size) {
        List<Book> filtered = getBooks();
        if (author != null && !author.trim().isEmpty()) {
            filtered = filtered.stream()
                    .filter(b -> b.getAuthor() != null && b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .toList();
        }
        if (page != null && size != null && page >= 0 && size > 0) {
            int skip = page * size;
            filtered = filtered.stream()
                    .skip(skip)
                    .limit(size)
                    .toList();
        }
        return filtered;
    }
}
