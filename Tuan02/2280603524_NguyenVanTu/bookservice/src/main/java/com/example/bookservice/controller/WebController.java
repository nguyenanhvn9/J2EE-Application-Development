package com.example.bookservice.controller;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    @Autowired
    private BookService bookService;

    // Test endpoint đơn giản
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "WebController is working! Time: " + System.currentTimeMillis();
    }

    // Test template endpoint
    @GetMapping("/test-form")
    public String testForm(Model model) {
        try {
            BookDTO book = new BookDTO();
            book.setTitle("Test Book");
            book.setAuthors(List.of("Test Author"));
            book.setSubjects(List.of("Test Subject"));
            book.setLanguages(List.of("en"));
            
            model.addAttribute("book", book);
            return "books/form";
        } catch (Exception e) {
            return "error";
        }
    }

    // === BOOK WEB ENDPOINTS ===

    // Hiển thị danh sách sách
    @GetMapping("/books")
    public String listBooks(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(required = false) String author) {
        try {
            System.out.println("=== WebController /books called ===");
            System.out.println("Parameters: page=" + page + ", size=" + size + ", author=" + author);
            
            List<BookDTO> books = bookService.getAllBooks(author, page, size);
            System.out.println("Retrieved " + books.size() + " books from service");
            
            long totalBooks = bookService.countBooks();
            System.out.println("Total books count: " + totalBooks);
            
            model.addAttribute("books", books);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalBooks", totalBooks);
            model.addAttribute("author", author != null ? author : "");
            model.addAttribute("totalPages", size > 0 ? (int) Math.ceil((double) totalBooks / size) : 1);
            
            System.out.println("Model attributes set, returning template");
            return "books/list";
        } catch (Exception e) {
            System.err.println("Error in /books endpoint: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("books", List.of());
            model.addAttribute("totalBooks", 0);
            model.addAttribute("currentPage", 0);
            model.addAttribute("pageSize", size);
            model.addAttribute("author", author != null ? author : "");
            model.addAttribute("totalPages", 1);
            return "books/list";
        }
    }

    // Hiển thị form thêm sách mới
    @GetMapping("/books/new")
    public String showCreateBookForm(Model model) {
        try {
            System.out.println("=== WebController /books/new called ===");
            BookDTO book = new BookDTO();
            // Khởi tạo các list rỗng để tránh null pointer trong template
            book.setAuthors(List.of());
            book.setSubjects(List.of());
            book.setLanguages(List.of());
            
            model.addAttribute("book", book);
            System.out.println("Book DTO created and added to model");
            return "books/form";
        } catch (Exception e) {
            System.err.println("Error in /books/new endpoint: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải form: " + e.getMessage());
            return "redirect:/books";
        }
    }

    // Xử lý thêm sách mới
    @PostMapping("/books")
    public String createBook(@Valid @ModelAttribute("book") BookDTO book,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "books/form";
        }

        try {
            bookService.createBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book created successfully!");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating book: " + e.getMessage());
            return "redirect:/books/new";
        }
    }

    // Hiển thị chi tiết sách
    @GetMapping("/books/{id}")
    public String showBookDetail(@PathVariable Long id, Model model) {
        try {
            BookDTO book = bookService.getBookById(id);
            model.addAttribute("book", book);
            return "books/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Book not found");
            return "redirect:/books";
        }
    }

    // Hiển thị form chỉnh sửa sách
    @GetMapping("/books/{id}/edit")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        try {
            BookDTO book = bookService.getBookById(id);
            model.addAttribute("book", book);
            return "books/form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Book not found");
            return "redirect:/books";
        }
    }

    // Xử lý cập nhật sách
    @PostMapping("/books/{id}")
    public String updateBook(@PathVariable Long id,
                            @Valid @ModelAttribute("book") BookDTO book,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "books/form";
        }

        try {
            Optional<BookDTO> updatedBook = bookService.updateBook(id, book);
            if (updatedBook.isPresent()) {
                redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Book not found");
            }
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating book: " + e.getMessage());
            return "redirect:/books/" + id + "/edit";
        }
    }

    // Xóa sách
    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = bookService.deleteBook(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Book not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting book: " + e.getMessage());
        }
        return "redirect:/books";
    }
}
