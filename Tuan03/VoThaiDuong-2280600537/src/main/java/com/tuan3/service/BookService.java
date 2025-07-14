package com.tuan3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tuan3.dto.BookDTO;
import com.tuan3.dto.GutendexResponse;
import com.tuan3.model.Book;

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

    public List<Book> getAllBooks(String author, int page, int size) {
        if (books.isEmpty())
            fetchBook();
        return books.stream()
                .filter(book -> author == null || book.getAuthor().equalsIgnoreCase(author))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    public List<Book> getAllBooks() {
        if (books.isEmpty())
            fetchBook();
        return books;
        //  return books.subList(0, Math.min(5, books.size()));
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId() == id).findFirst();
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

    public void updateBook(Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == updatedBook.getId())
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }

    public Optional<Book> deleteBook(long id) {
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
