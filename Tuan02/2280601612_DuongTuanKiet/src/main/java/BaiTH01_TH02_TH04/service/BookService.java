package BaiTH01_TH02_TH04.service;

import BaiTH01_TH02_TH04.dto.BookDTO;
import BaiTH01_TH02_TH04.dto.BookResponseDTO;
import BaiTH01_TH02_TH04.exception.ResourceNotFoundException;
import BaiTH01_TH02_TH04.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    // ✅ Lazy loading kết hợp caching
    @Cacheable("books")
    public void fetchBooks() {
        if (!books.isEmpty()) return;

        String url = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();

        BookResponseDTO response = restTemplate.getForObject(url, BookResponseDTO.class);
        if (response != null && response.getResults() != null) {
            for (BookDTO dto : response.getResults()) {
                books.add(new Book(dto.getId(), dto.getTitle(), "Unknown"));
            }
        }
    }

    // ✅ Lấy toàn bộ + phân trang + lọc author
    public List<Book> getBooks(String author, int page, int size) {
//        fetchBooks();
        Stream<Book> stream = books.stream();

        if (author != null && !author.isEmpty()) {
            stream = stream.filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()));
        }

        return stream
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    // ✅ Lấy theo ID có xử lý ngoại lệ
    public Book getBookById(int id) {
        fetchBooks();
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }

    // ✅ Thêm sách mới
    public void addBook(Book book) {
        fetchBooks();
        validateBook(book);
        if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        books.add(book);
    }

    // ✅ Cập nhật sách
    public Optional<Book> updateBook(int id, Book updatedBook) {
        fetchBooks();
        validateBook(updatedBook);
        Optional<Book> existing = books.stream().filter(b -> b.getId() == id).findFirst();
        if (existing.isEmpty()) return Optional.empty();

        Book book = existing.get();
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        return Optional.of(book);
    }

    // ✅ Xoá sách
    public boolean deleteBook(int id) {
        fetchBooks();
        Optional<Book> existing = books.stream().filter(b -> b.getId() == id).findFirst();
        return existing.map(books::remove).orElse(false);
    }

    // ✅ Tìm kiếm nâng cao theo từ khoá
    public List<Book> searchBooks(String keyword) {
        fetchBooks();
        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerKeyword)
                        || book.getAuthor().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    // ✅ Validate dữ liệu đầu vào
    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }
    }
}
