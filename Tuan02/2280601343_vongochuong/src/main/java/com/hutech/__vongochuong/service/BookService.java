package com.hutech.__vongochuong.service;

import com.hutech.__vongochuong.model.Book;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean updateBook(int id, Book updatedBook) {
        Optional<Book> bookOpt = getBookById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            return true;
        }
        return false;
    }

    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }

    public List<Book> searchBooks(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerKeyword) ||
                book.getAuthor().toLowerCase().contains(lowerKeyword)) {
                result.add(book);
            }
        }
        return result;
    }
} 