package BaiTH01_TH02_TH04.controller;

import BaiTH01_TH02_TH04.model.Book;
import BaiTH01_TH02_TH04.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Hiển thị danh sách sách (books.html)
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getBooks(null, 0, 100));
        return "books";
    }

    // Hiển thị form thêm sách mới (add-book.html)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    // Xử lý thêm sách
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    // Hiển thị form chỉnh sửa sách (edit-book.html)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "edit-book";
    }

    // Xử lý cập nhật sách
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute("book") Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        return "redirect:/books";
    }

    // Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
