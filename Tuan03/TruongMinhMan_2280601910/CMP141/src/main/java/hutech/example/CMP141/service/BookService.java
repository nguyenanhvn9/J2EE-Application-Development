package hutech.example.CMP141.service;

import hutech.example.CMP141.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    public List<Book> getAllBooks() {
        if (books.isEmpty()) {
            fetchRandomBooksFromApi();
        }
        // Luôn trả về tối đa 5 sách đầu tiên
        return books.size() > 5 ? books.subList(0, 5) : books;
    }

    private void fetchRandomBooksFromApi() {
        books.clear(); // Xóa danh sách cũ trước khi thêm mới
        String url = "https://gutendex.com/books";
        RestTemplate restTemplate = new RestTemplate();
        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);
        if (response != null && response.results != null && !response.results.isEmpty()) {
            List<GutenBook> all = response.results;
            Random rand = new Random();
            for (int i = 0; i < 5; i++) {
                GutenBook gb = all.get(rand.nextInt(all.size()));
                Book book = new Book();
                book.setId(nextId++);
                book.setTitle(gb.title);
                book.setAuthor(gb.authors != null && !gb.authors.isEmpty() ? gb.authors.get(0).name : "Unknown");
                book.setIsbn("");
                book.setPrice(0);
                book.setYear(0);
                books.add(book);
            }
        }
    }

    public void addBook(Book book) {
        book.setId(nextId++);
        books.add(0, book); // Thêm vào đầu danh sách
        if (books.size() > 5) {
            books.remove(5); // Xóa sách cuối nếu quá 5
        }
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public void updateBook(Book updatedBook) {
        books.stream()
            .filter(book -> book.getId().equals(updatedBook.getId()))
            .findFirst()
            .ifPresent(book -> {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                // Nếu có các trường khác, cập nhật thêm ở đây
            });
    }

    public void deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    // DTOs nội bộ cho parse JSON
    private static class GutendexResponse {
        public List<GutenBook> results;
    }
    private static class GutenBook {
        public String title;
        public List<GutenAuthor> authors;
    }
    private static class GutenAuthor {
        public String name;
    }
}