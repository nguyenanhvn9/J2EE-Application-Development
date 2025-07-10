package com.example.demo.Service;

import com.example.demo.model.Book;
import com.example.demo.dto.GutendexResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private RestTemplate restTemplate;

    private List<Book> books = null;

    @Cacheable("books")
    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        List<Book> filtered = getAllBooks();
        if (author != null && !author.trim().isEmpty()) {
            String lower = author.toLowerCase();
            filtered = filtered.stream().filter(book ->
                book.getAuthors() != null &&
                book.getAuthors().stream().anyMatch(a -> a.getName() != null && a.getName().toLowerCase().contains(lower))
            ).collect(Collectors.toList());
        }
        if (page != null && size != null && page >= 0 && size > 0) {
            int skip = page * size;
            filtered = filtered.stream().skip(skip).limit(size).collect(Collectors.toList());
        }
        return filtered;
    }

    // Internal method to get all books without filtering/pagination
    public List<Book> getAllBooks() {
        if (books == null) {
            books = fetchBooksFromApi();
        }
        return books;
    }

    public void refreshBooksFromApi() {
        books = fetchBooksFromApi();
    }

    public List<Book> fetchBooksFromApi() {
        String url = "https://gutendex.com/books";
        GutendexResponseDTO response = restTemplate.getForObject(url, GutendexResponseDTO.class);
        if (response == null || response.getResults() == null) {
            return List.of();
        }
        return response.getResults().stream().map(dto -> {
            Book book = new Book();
            book.setId(dto.getId());
            book.setTitle(dto.getTitle());
            if (dto.getAuthors() != null) {
                List<Book.Author> authors = dto.getAuthors().stream().map(a -> {
                    Book.Author author = new Book.Author();
                    author.setName(a.getName());
                    return author;
                }).collect(Collectors.toList());
                book.setAuthors(authors);
            }
            return book;
        }).collect(Collectors.toList());
    }

    // Validate dữ liệu đầu vào
    private void validateBook(Book book) {
        if (book == null) throw new IllegalArgumentException("Book cannot be null");
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if (book.getAuthors() == null || book.getAuthors().isEmpty() ||
            book.getAuthors().stream().anyMatch(a -> a.getName() == null || a.getName().trim().isEmpty())) {
            throw new IllegalArgumentException("Book must have at least one author with a valid name");
        }
    }

    // Thêm mới sách, kiểm tra trùng lặp ID
    public boolean addBook(Book book) {
        validateBook(book);
        if (getAllBooks().stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        books.add(book);
        return true;
    }

    // Cập nhật sách, trả về Optional<Book>
    public Optional<Book> updateBook(int id, Book book) {
        validateBook(book);
        for (int i = 0; i < getAllBooks().size(); i++) {
            if (books.get(i).getId() == id) {
                book.setId(id); // Đảm bảo id không bị thay đổi
                books.set(i, book);
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    // Xóa sách, trả về boolean
    public boolean deleteBook(int id) {
        Iterator<Book> iterator = getAllBooks().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    // Tìm kiếm nâng cao
    public List<Book> searchBooks(String keyword) {
        if (keyword == null) return List.of();
        String lower = keyword.toLowerCase();
        return getAllBooks().stream().filter(book ->
            (book.getTitle() != null && book.getTitle().toLowerCase().contains(lower)) ||
            (book.getAuthors() != null && book.getAuthors().stream().anyMatch(a -> a.getName() != null && a.getName().toLowerCase().contains(lower)))
        ).collect(Collectors.toList());
    }

    public Book getBookById(int id) {
        return getAllBooks().stream().filter(b -> b.getId() == id).findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }
}