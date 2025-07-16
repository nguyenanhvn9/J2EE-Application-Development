package com.example.demo.service;

import com.example.demo.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookService {
   private List<Book> books = new ArrayList<Book>();
   private int nextId = 1;

   public BookService() {
   }

   public List<Book> getAllBooks() {
      return this.books;
   }

   public void addBook(Book book) {
      book.setId(this.nextId++);
      this.books.add(book);
   }

   public Optional<Book> getBookById(int id) {
      return this.books.stream().filter((book) -> {
         return book.getId() == id;
      }).findFirst();
   }

   public void updateBook(Book updatedBook) {
      for(int i = 0; i < this.books.size(); ++i) {
         if (((Book)this.books.get(i)).getId() == updatedBook.getId()) {
            this.books.set(i, updatedBook);
            return;
         }
      }

   }

   public void deleteBook(int id) {
      this.books.removeIf((book) -> {
         return book.getId() == id;
      });
   }
}
