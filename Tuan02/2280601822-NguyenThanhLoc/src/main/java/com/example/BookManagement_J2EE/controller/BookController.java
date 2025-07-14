package com.example.BookManagement_J2EE.controller;

import com.example.BookManagement_J2EE.DTO.BookDTO;
import com.example.BookManagement_J2EE.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.example.BookManagement_J2EE.model.Book;

@RestController
@RequestMapping("/api/books") // Base URL cho tất cả API trong controller
public class BookController {
  @Autowired  
  private BookService bookService;
  // 1. Lấy danh sách tất cả sách
  @GetMapping
  public List<BookDTO> getAllBooks(
      @RequestParam(required = false) String author,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return bookService.getAllBooks(author, page, size);
  }

  // Thêm sách mới
  @PostMapping
  public String addBook(@RequestBody BookDTO bookDTO) {
    bookService.addBook(bookDTO);
    return "Book added successfully!";
  }

  // Cập nhật sách
  @PutMapping("/{id}")
  public Object updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
    Optional<BookDTO> updated = bookService.updateBook(id, bookDTO);
    if (updated.isPresent()) {
      return updated.get();
    } else {
      return "Book not found!";
    }
  }

  // Xóa sách
  @DeleteMapping("/{id}")
  public String deleteBook(@PathVariable int id) {
    boolean deleted = bookService.deleteBook(id);
    if (deleted) {
      return "Book deleted successfully!";
    } else {
      return "Book not found!";
    }
  }

  // Tìm kiếm nâng cao
  @GetMapping("/search")
  public List<BookDTO> searchBooks(@RequestParam String keyword) {
    return bookService.searchBooks(keyword);
  }

  @GetMapping("/{id}")
  public BookDTO getBookById(@PathVariable int id) {
    Book book = bookService.getBookById(id);
    BookDTO dto = new BookDTO();
    dto.setId(book.getId());
    dto.setTitle(book.getTitle());
    BookDTO.AuthorDTO authorDTO = new BookDTO.AuthorDTO();
    authorDTO.setName(book.getAuthor());
    dto.setAuthors(java.util.Collections.singletonList(authorDTO));
    dto.setLanguages(java.util.Collections.singletonList("en"));
    return dto;
  }
}