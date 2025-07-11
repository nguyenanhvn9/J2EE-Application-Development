package hutech.example.CMP141.service;

import hutech.example.CMP141.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import hutech.example.CMP141.dto.BookListDTO;
import hutech.example.CMP141.dto.GutenBookDTO;
import hutech.example.CMP141.dto.AuthorDTO;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import hutech.example.CMP141.exception.ResourceNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@Service
public class BookService {
    
    private List<Book> books = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);
    
    @Autowired
    private RestTemplate restTemplate;
    private boolean loadedFromApi = false;
    
    public BookService() {
        // Thêm một số sách mẫu
        books.add(new Book(idCounter.getAndIncrement(), "Java Programming", "John Doe", "1234567890", 29.99, 2023));
        books.add(new Book(idCounter.getAndIncrement(), "Spring Boot Guide", "Jane Smith", "0987654321", 39.99, 2024));
        books.add(new Book(idCounter.getAndIncrement(), "Database Design", "Bob Johnson", "1122334455", 24.99, 2022));
    }
    
    @Cacheable("books")
    public void fetchFromApi() {
        String url = "https://gutendex.com/books";
        BookListDTO response = restTemplate.getForObject(url, BookListDTO.class);
        if (response != null && response.getResults() != null) {
            for (GutenBookDTO dto : response.getResults()) {
                Book book = new Book();
                book.setId(dto.getId());
                book.setTitle(dto.getTitle());
                // Lấy tên tác giả đầu tiên nếu có
                if (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) {
                    book.setAuthor(dto.getAuthors().get(0).getName());
                } else {
                    book.setAuthor("Unknown");
                }
                book.setIsbn("");
                book.setPrice(0);
                book.setYear(0);
                books.add(book);
            }
        }
    }

    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        if (!loadedFromApi) {
            fetchFromApi();
            loadedFromApi = true;
        }
        Stream<Book> stream = books.stream();
        if (author != null && !author.isEmpty()) {
            String lower = author.toLowerCase();
            stream = stream.filter(b -> b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lower));
        }
        if (page != null && size != null && page >= 0 && size > 0) {
            stream = stream.skip((long) page * size).limit(size);
        }
        return stream.collect(Collectors.toList());
    }
    
    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }
    
    public boolean addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title must not be empty.");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author must not be empty.");
        }
        boolean exists = books.stream().anyMatch(b -> b.getId() == book.getId());
        if (exists) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        book.setId(idCounter.getAndIncrement());
        books.add(book);
        return true;
    }

    public Optional<Book> updateBook(int id, Book updatedBook) {
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title must not be empty.");
        }
        if (updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author must not be empty.");
        }
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                updatedBook.setId(id);
                books.set(i, updatedBook);
                return Optional.of(updatedBook);
            }
        }
        return Optional.empty();
    }

    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null) return List.of();
        String lower = keyword.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if ((book.getTitle() != null && book.getTitle().toLowerCase().contains(lower)) ||
                (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lower))) {
                result.add(book);
            }
        }
        return result;
    }
}