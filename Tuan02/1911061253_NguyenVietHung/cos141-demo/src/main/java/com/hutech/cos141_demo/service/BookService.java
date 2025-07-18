package com.hutech.cos141_demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hutech.cos141_demo.model.Book;
import com.hutech.cos141_demo.model.BookItemDTO;
import com.hutech.cos141_demo.model.BookResponseDTO;
import com.hutech.cos141_demo.model.AuthorDTO;
import com.hutech.cos141_demo.exception.ResourceNotFoundException;
import org.springframework.cache.annotation.Cacheable;

@Service
public class BookService {
private List<Book> books = new ArrayList<>();

@Cacheable("books")
public void fetchBooksFromApi() {
String url = "https://gutendex.com/books";
RestTemplate restTemplate = new RestTemplate();
BookResponseDTO response = restTemplate.getForObject(url, BookResponseDTO.class);

if (response != null && response.getResults() != null) {
for (BookItemDTO item : response.getResults()) {
Book book = new Book();
book.setId(item.getId());
book.setTitle(item.getTitle());
// Bạn có thể ánh xạ thêm các trường khác nếu muốn
books.add(book);
}
}
}

public List<Book> getBooks(String author, Integer page, Integer size) {
    if (books.isEmpty()) {
        fetchBooksFromApi();
    }
    // Lọc theo author nếu có
    List<Book> filtered = books;
    if (author != null && !author.trim().isEmpty()) {
        String lowerAuthor = author.toLowerCase();
        filtered = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerAuthor)) {
                filtered.add(book);
            }
        }
    }
    // Phân trang nếu có
    if (page != null && size != null && page >= 0 && size > 0) {
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, filtered.size());
        if (fromIndex >= filtered.size()) {
            return new ArrayList<>();
        }
        return filtered.subList(fromIndex, toIndex);
    }
    return filtered;
}

public Book getBookById(int id) {
    return books.stream().filter(book -> book.getId() == id)
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found."));
}
public boolean addBook(Book book) {
    // Validate input
    if (book.getTitle() == null || book.getTitle().trim().isEmpty() ||
        book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
        throw new IllegalArgumentException("Book title and author must not be null or empty.");
    }
    // Check duplicate ID
    if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
        throw new IllegalArgumentException("Book with this ID already exists.");
    }
    books.add(book);
    return true;
}

public Optional<Book> updateBook(int id, Book updatedBook) {
    // Validate input
    if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty() ||
        updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
        throw new IllegalArgumentException("Book title and author must not be null or empty.");
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
    if (keyword == null) return new ArrayList<>();
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