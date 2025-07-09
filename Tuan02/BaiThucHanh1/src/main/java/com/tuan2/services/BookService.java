package com.tuan2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.tuan2.models.Book;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }
}
