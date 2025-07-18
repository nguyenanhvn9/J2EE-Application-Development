package com.hutech.book_user_management.service;

import com.hutech.book_user_management.dto.BookResponseDTO;
import com.hutech.book_user_management.dto.ResultDTO;
import com.hutech.book_user_management.exception.ResourceNotFoundException;
import com.hutech.book_user_management.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();

    public void fetch() {
        String url = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();
        BookResponseDTO response = restTemplate.getForObject(url, BookResponseDTO.class);

        if (response != null && response.getResults() != null) {
            for (ResultDTO dto : response.getResults()) {
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
    public List<Book> getBooksFromApi() {
        if (books.isEmpty()) {
            fetch();
        }
        return books;
    }

    public List<Book> getBooks(String author, int page, int size) {
        List<Book> allBooks = getBooksFromApi();

        return allBooks.stream()
                .filter(book -> author == null || book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    public Book getBookById(int id) {
        return getBooksFromApi().stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }

    public boolean addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty()
                || book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and author must not be null or empty.");
        }

        for (Book b : books) {
            if (b.getId() == book.getId()) {
                return false;
            }
        }

        books.add(book);
        return true;
    }

    public boolean updateBook(Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty()
                || book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and author must not be null or empty.");
        }

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                return true;
            }
        }

        return false;
    }

    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String lowerKeyword = keyword.toLowerCase();

        return getBooksFromApi().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerKeyword)
                        || book.getAuthor().toLowerCase().contains(lowerKeyword))
                .toList();
    }
}
