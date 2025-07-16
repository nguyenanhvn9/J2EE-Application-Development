package leducanh.name.vn.leducanh_2280600056.controller;

import leducanh.name.vn.leducanh_2280600056.model.Book;
import leducanh.name.vn.leducanh_2280600056.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookViewController {

    private final BookService bookService;

    @Autowired
    public BookViewController(BookService bookService) {
        this.bookService = bookService;
    }

    // Trang danh sách sách
    @GetMapping
    public String listBooks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        List<Book> pageBooks = bookService.getBooks(page, size);

        // Thêm chuỗi hiển thị nếu cần
        for (Book b : pageBooks) {
            b.setAuthorsStr(String.join(", ", b.getAuthors()));
            b.setSubjectsStr(String.join(", ", b.getSubjects()));
            b.setBookshelvesStr(String.join(", ", b.getBookshelves()));
            b.setLanguagesStr(String.join(", ", b.getLanguages()));
            b.setImageUrl(b.getImageUrl());
        }

        int totalPages = bookService.getTotalPages(size);

        model.addAttribute("books", pageBooks);
        model.addAttribute("book", new Book());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        return "books";
    }

    // Thêm mới hoặc cập nhật
    @PostMapping
    public String addOrUpdateBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            if (book.getId() == null || book.getId().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("msg", "ID không được để trống khi thêm sách!");
                return "redirect:/books";
            }

            boolean isNew = bookService.getAllBooks().stream()
                    .noneMatch(b -> b.getId().equals(book.getId()));

            if (isNew) {
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

    // Sửa (render lại index với book cần sửa)
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable String id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    // Xoá
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBookById(id);
            redirectAttributes.addFlashAttribute("msg", "Xoá sách thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Lỗi khi xoá: " + e.getMessage());
        }
        return "redirect:/books";
    }
}
