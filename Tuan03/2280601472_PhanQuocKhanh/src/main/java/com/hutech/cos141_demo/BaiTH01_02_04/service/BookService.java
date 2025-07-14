package com.hutech.cos141_demo.BaiTH01_02_04.service;

import com.hutech.cos141_demo.BaiTH01_02_04.model.AuthorDTO;
import com.hutech.cos141_demo.BaiTH01_02_04.model.Book;
import com.hutech.cos141_demo.BaiTH01_02_04.model.BookApiResponseDTO;
import com.hutech.cos141_demo.BaiTH01_02_04.model.BookDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    @Cacheable("books")
    public void fetch() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://gutendex.com/books";
        BookApiResponseDTO response = restTemplate.getForObject(url, BookApiResponseDTO.class);
        if (response != null && response.getResults() != null) {
            for (BookDTO bookDTO : response.getResults()) {
                Book book = new Book();
                book.setId(bookDTO.getId());
                book.setTitle(bookDTO.getTitle());
                // Lấy tên tác giả
                List<String> authorNames = new ArrayList<>();
                if (bookDTO.getAuthors() != null) {
                    authorNames = bookDTO.getAuthors().stream()
                        .map(AuthorDTO::getName)
                        .collect(Collectors.toList());
                }
                book.setAuthors(authorNames);
                books.add(book);
            }
        }
    }

    public List<Book> getBooks(String author, Integer page, Integer size) {
        // Lọc theo author nếu có
        java.util.stream.Stream<Book> stream = books.stream();
        if (author != null && !author.trim().isEmpty()) {
            String lowerAuthor = author.toLowerCase();
            stream = stream.filter(b -> b.getAuthors() != null && b.getAuthors().stream().anyMatch(a -> a != null && a.toLowerCase().contains(lowerAuthor)));
        }
        // Phân trang nếu có
        if (page != null && size != null && page >= 0 && size > 0) {
            stream = stream.skip((long) page * size).limit(size);
        }
        return stream.collect(java.util.stream.Collectors.toList());
    }

    // Lấy sách theo id, ném ResourceNotFoundException nếu không tìm thấy
    public Book getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    // Thêm sách mới, kiểm tra trùng lặp ID và validate dữ liệu
    public boolean addBook(Book book) {
        if (book == null) throw new IllegalArgumentException("Book cannot be null.");
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (book.getAuthors() == null || book.getAuthors().isEmpty() || book.getAuthors().stream().anyMatch(a -> a == null || a.trim().isEmpty())) {
            throw new IllegalArgumentException("Book must have at least one valid author.");
        }
        boolean exists = books.stream().anyMatch(b -> b.getId() == book.getId());
        if (exists) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        books.add(0, book); // Thêm vào đầu danh sách
        return true;
    }

    // Cập nhật sách, trả về Optional<Book> nếu thành công, Optional.empty nếu không tìm thấy
    public java.util.Optional<Book> updateBook(Book book) {
        if (book == null) throw new IllegalArgumentException("Book cannot be null.");
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (book.getAuthors() == null || book.getAuthors().isEmpty() || book.getAuthors().stream().anyMatch(a -> a == null || a.trim().isEmpty())) {
            throw new IllegalArgumentException("Book must have at least one valid author.");
        }
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                return java.util.Optional.of(book);
            }
        }
        return java.util.Optional.empty();
    }

    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null) return new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(b -> (b.getTitle() != null && b.getTitle().toLowerCase().contains(lowerKeyword)) ||
                        (b.getAuthors() != null && b.getAuthors().stream().anyMatch(a -> a != null && a.toLowerCase().contains(lowerKeyword))))
                .collect(Collectors.toList());
    }
} 