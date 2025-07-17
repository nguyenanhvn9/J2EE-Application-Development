package hutech.example.CMP141.service;

import hutech.example.CMP141.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final String url = "https://gutendex.com/books";
    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    public BookService() {
        books.add(new Book(nextId++, "Java Programming", "John Doe", "1234567890", 29.99, 2023));
        books.add(new Book(nextId++, "Spring Boot Guide", "Jane Smith", "0987654321", 39.99, 2024));
        books.add(new Book(nextId++, "Database Design", "Bob Johnson", "1122334455", 24.99, 2022));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void addBook(Book book) {
        book.setId(nextId++);
        books.add(book);
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public void updateBook(Book updatedBook) {
        books.stream()
            .filter(book -> book.getId().equals(updatedBook.getId()))
            .findFirst()
            .ifPresent(book -> {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setIsbn(updatedBook.getIsbn());
                book.setPrice(updatedBook.getPrice());
                book.setYear(updatedBook.getYear());
            });
    }

    public void deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}