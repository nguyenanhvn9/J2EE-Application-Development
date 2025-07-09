package com.tuan2.services;
import com.tuan2.ExceptionHandling.ResourceNotFoundException;
import com.tuan2.dto.BookDTO;
import com.tuan2.dto.GutendexResponse;
import com.tuan2.models.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);


    public List<Book> getAllBooks(String author, int page, int size) {
        if (books.isEmpty())  fetchBooksFromGutenberg();
        return books.stream()
                .filter(book -> author == null || book.getAuthor().equalsIgnoreCase(author))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    public Book getBookById(Long id) {
        if (books.isEmpty())  fetchBooksFromGutenberg();
        return books.stream()
                .filter(book -> book.getId() == (id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }

    // 🔥 Fetch từ API công khai
    public void fetchBooksFromGutenberg() {
        String apiUrl = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();

        GutendexResponse response = restTemplate.getForObject(apiUrl, GutendexResponse.class);

        if (response != null && response.getResults() != null) {
            for (BookDTO dto : response.getResults()) {
                String authorName = (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) ?
                        dto.getAuthors().get(0).getName() : "Unknown";

                Book book = new Book((int)idCounter.getAndIncrement(), dto.getTitle(), authorName);
                books.add(book);
            }
        }
    }
    public void addBook(Book book) {
        if (book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null");
        }

        if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }

        books.add(book);
    }

    public Optional<Book> updateBook(int id, Book updatedBook) {

        if (updatedBook.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null");
        }

        Optional<Book> bookOpt = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            return Optional.of(book);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Book> deleteBook(int id) {
        Optional<Book> bookOpt = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
        bookOpt.ifPresent(books::remove);
        return bookOpt;
    }

    public List<Book> searchBooks(String keyWord) {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            boolean matchesTitle = (keyWord == null || keyWord.isEmpty()) ||
                    book.getTitle().toLowerCase().contains(keyWord.toLowerCase())
                    || book.getAuthor().toLowerCase().contains(keyWord.toLowerCase());
            if (matchesTitle) {
                result.add(book);
            }
        }
        return result;
    }
}
