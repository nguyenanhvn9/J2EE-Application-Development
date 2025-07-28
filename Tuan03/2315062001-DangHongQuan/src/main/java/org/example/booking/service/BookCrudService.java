package org.example.booking.service;

import org.example.booking.model.BookCrud;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BookCrudService {

    private final Map<Long, BookCrud> store = new HashMap<>();
    private Long idCounter = 1L;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<BookCrud> getAll() {
        return new ArrayList<>(store.values());
    }

    public BookCrud getById(Long id) {
        return store.get(id);
    }

    public BookCrud create(BookCrud book) {
        book.setId(idCounter++);
        store.put(book.getId(), book);
        return book;
    }

    public BookCrud update(Long id, BookCrud updatedBook) {
        BookCrud book = store.get(id);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
        }
        return book;
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }

    /**
     * Fetch 5 sách đầu tiên từ Gutendex và ghi đè dữ liệu hiện tại
     */
    public void fetchFromGutendex() {
        String url = "https://gutendex.com/books";
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("results")) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

            store.clear(); // Xóa dữ liệu cũ
            idCounter = 1L;

            for (int i = 0; i < Math.min(5, results.size()); i++) {
                Map<String, Object> item = results.get(i);

                String title = (String) item.get("title");
                String author = "Unknown";
                List<Map<String, Object>> authors = (List<Map<String, Object>>) item.get("authors");
                if (authors != null && !authors.isEmpty()) {
                    author = (String) authors.get(0).get("name");
                }

                BookCrud book = new BookCrud();
                book.setTitle(title);
                book.setAuthor(author);
                create(book);
            }
        }
    }
}
