package leducanh.name.vn.leducanh_2280600056.controller;

import leducanh.name.vn.leducanh_2280600056.model.Books;
import leducanh.name.vn.leducanh_2280600056.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookViewController {

    private final BookService bookService;

    @Autowired
    public BookViewController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        List<Books> pageBooks = bookService.getBooks(page, size);
        int totalPages = bookService.getTotalPages(size);

        model.addAttribute("books", pageBooks);
        model.addAttribute("book", new Books()); // Form binding
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        return "books"; // Thymeleaf template
    }

    @PostMapping
    public String addOrUpdateBook(@ModelAttribute Books book, RedirectAttributes redirectAttributes) {
        try {
            if (book.getId() == null) {
                bookService.addBook(book);
                redirectAttributes.addFlashAttribute("msg", "Đã thêm sách mới thành công!");
            } else {
                bookService.updateBookById(book.getId(), book);
                redirectAttributes.addFlashAttribute("msg", "Đã cập nhật sách thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Lỗi: " + e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Books book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("size", 5);
        return "books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBookById(id);
            redirectAttributes.addFlashAttribute("msg", "Đã xoá sách thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Lỗi khi xoá: " + e.getMessage());
        }
        return "redirect:/books";
    }
}
