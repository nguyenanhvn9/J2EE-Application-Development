package com.example.bookservice.controller;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookWebController {

    @Autowired
    private BookService bookService;

    // GET /books - Hiển thị danh sách sách dạng bảng
    @GetMapping
    public String listBooks(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        try {
            // Lấy danh sách sách với phân trang
            List<BookDTO> allBooks = bookService.getAllBooks(author, page, size);
            long totalBooks = bookService.countBooks();
            
            // Thêm dữ liệu vào model
            model.addAttribute("books", allBooks);
            model.addAttribute("totalBooks", totalBooks);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", (int) Math.ceil((double) totalBooks / size));
            model.addAttribute("pageSize", size);
            model.addAttribute("author", author != null ? author : "");
            
            return "books/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách sách: " + e.getMessage());
            return "books/list";
        }
    }

    // GET /books/grid - Hiển thị danh sách sách dạng card
    @GetMapping("/grid")
    public String gridBooks(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        
        try {
            // Lấy danh sách sách với phân trang
            List<BookDTO> allBooks = bookService.getAllBooks(author, page, size);
            long totalBooks = bookService.countBooks();
            
            // Thêm dữ liệu vào model
            model.addAttribute("books", allBooks);
            model.addAttribute("totalBooks", totalBooks);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", (int) Math.ceil((double) totalBooks / size));
            model.addAttribute("pageSize", size);
            model.addAttribute("author", author != null ? author : "");
            
            return "books/grid-view";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách sách: " + e.getMessage());
            return "books/grid-view";
        }
    }

    // GET /books/{id} - Hiển thị chi tiết sách
    @GetMapping("/{id}")
    public String bookDetail(@PathVariable Long id, Model model) {
        try {
            BookDTO book = bookService.getBookById(id);
            model.addAttribute("book", book);
            return "books/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không tìm thấy sách với ID: " + id);
            return "redirect:/books";
        }
    }

    // GET /books/new - Hiển thị form thêm sách mới
    @GetMapping("/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new BookDTO());
        return "books/form";
    }

    // POST /books - Xử lý tạo sách mới
    @PostMapping
    public String createBook(@ModelAttribute BookDTO book, RedirectAttributes redirectAttributes) {
        try {
            bookService.createBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sách mới thành công!");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thêm sách: " + e.getMessage());
            return "redirect:/books/new";
        }
    }

    // GET /books/{id}/edit - Hiển thị form chỉnh sửa sách
    @GetMapping("/{id}/edit")
    public String editBookForm(@PathVariable Long id, Model model) {
        try {
            BookDTO book = bookService.getBookById(id);
            model.addAttribute("book", book);
            return "books/form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không tìm thấy sách với ID: " + id);
            return "redirect:/books";
        }
    }

    // POST /books/{id} - Xử lý cập nhật sách
    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute BookDTO book, 
                           RedirectAttributes redirectAttributes) {
        try {
            bookService.updateBook(id, book);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sách thành công!");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật sách: " + e.getMessage());
            return "redirect:/books/" + id + "/edit";
        }
    }

    // POST /books/{id}/delete - Xử lý xóa sách
    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = bookService.deleteBook(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "Xóa sách thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sách!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi xóa sách: " + e.getMessage());
        }
        return "redirect:/books";
    }

    // GET /books/search - Tìm kiếm sách
    @GetMapping("/search")
    public String searchBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        try {
            List<BookDTO> books;
            if (keyword != null && !keyword.trim().isEmpty()) {
                books = bookService.searchBooks(keyword);
            } else if (title != null && !title.trim().isEmpty()) {
                books = bookService.searchByTitle(title);
            } else if (author != null && !author.trim().isEmpty()) {
                books = bookService.searchByAuthor(author);
            } else {
                books = bookService.getAllBooks();
            }
            
            model.addAttribute("books", books);
            model.addAttribute("totalBooks", books.size());
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", books.size());
            model.addAttribute("searchKeyword", keyword);
            model.addAttribute("searchTitle", title);
            model.addAttribute("searchAuthor", author);
            
            return "books/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tìm kiếm: " + e.getMessage());
            return "books/list";
        }
    }
}
