package com.tuan2.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tuan2.dto.BookDTO;
import com.tuan2.dto.GutendexResponse;
import com.tuan2.models.Book;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public boolean fetchBook() {
        
        try {
            final RestTemplate restTemplate = new RestTemplate();
            String url = "https://gutendex.com/books";
            GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);

            if (response != null && response.getResults() != null) {
                for (BookDTO dto : response.getResults()) {
                    String authorName = dto.getAuthors().isEmpty() ? "Unknown" : dto.getAuthors().get(0).getName();
                    Book book = new Book(dto.getId(), dto.getTitle(), authorName);
                    addBook(book);
                }
            }
            return true;
        } catch (Exception e) {
            throw new IllegalAccessError("Cannot call api.");
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

    public List<Book> getBooks() {
        return books;
    }

    public Book getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
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
