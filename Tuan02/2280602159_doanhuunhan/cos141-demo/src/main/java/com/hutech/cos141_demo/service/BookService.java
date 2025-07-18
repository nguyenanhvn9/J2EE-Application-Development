package com.hutech.cos141_demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import com.hutech.cos141_demo.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.hutech.cos141_demo.dto.GutendexResponseDTO;
import com.hutech.cos141_demo.dto.GutendexBookDTO;
import com.hutech.cos141_demo.model.Book;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    // Lazy loading: fetch books from public API
    public void fetchBooksFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://gutendex.com/books";
        GutendexResponseDTO response = restTemplate.getForObject(url, GutendexResponseDTO.class);
        if (response != null && response.getResults() != null) {
            for (GutendexBookDTO dto : response.getResults()) {
                String author = "Unknown";
                if (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) {
                    author = dto.getAuthors().get(0).getName();
                }
                Book book = new Book(dto.getId(), dto.getTitle(), author);
                books.add(book);
            }
        }
    }


    @Cacheable("books")
    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        List<Book> filtered = books;
        if (author != null && !author.trim().isEmpty()) {
            String lowerAuthor = author.toLowerCase();
            filtered = books.stream()
                .filter(b -> b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lowerAuthor))
                .collect(Collectors.toList());
        }
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
            .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }

    /**
     * Thêm sách mới vào danh sách. Nếu id đã tồn tại, ném IllegalArgumentException.
     * Nếu title hoặc author null/rỗng, ném IllegalArgumentException.
     * @param book sách cần thêm
     * @return true nếu thêm thành công
     */
    public boolean addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty() ||
            book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title and author must not be null or empty.");
        }
        boolean exists = books.stream().anyMatch(b -> b.getId() == book.getId());
        if (exists) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        books.add(book);
        return true;
    }

    /**
     * Cập nhật sách theo id. Trả về Optional<Book> nếu cập nhật thành công, Optional.empty() nếu không tìm thấy.
     */
    public Optional<Book> updateBook(int id, Book updatedBook) {
        if (updatedBook == null || updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty() ||
            updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title and author must not be null or empty.");
        }
        Optional<Book> found = books.stream().filter(book -> book.getId() == id).findFirst();
        found.ifPresent(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
        });
        return found;
    }

    /**
     * Xóa sách theo id. Trả về true nếu xóa thành công, false nếu không tìm thấy.
     */
    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
    /**
     * Tìm kiếm sách theo từ khóa (không phân biệt hoa thường) trong title hoặc author.
     * @param keyword từ khóa tìm kiếm
     * @return danh sách sách phù hợp
     */
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
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
