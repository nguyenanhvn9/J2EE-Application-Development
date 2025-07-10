package leducanh.name.vn.leducanh_2280600056.service;

import leducanh.name.vn.leducanh_2280600056.dto.Book.GutendexBook;
import leducanh.name.vn.leducanh_2280600056.dto.Book.GutendexResponse;
import leducanh.name.vn.leducanh_2280600056.exception.ResourceNotFoundException;
import leducanh.name.vn.leducanh_2280600056.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final RestTemplate restTemplate;
    private final List<Book> books = new ArrayList<>();

    @Autowired
    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void fetchBooksFromGutendex() {
        try {
            String url = "https://gutendex.com/books";
            GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);

            if (response != null && response.getResults() != null) {
                List<Book> newBooks = response.getResults().stream()
                        .map(this::convertToBook)
                        .collect(Collectors.toList());

                // Thêm vào danh sách books
                System.out.println("Số lượng sách mới: " + newBooks.size());
                books.addAll(newBooks);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi fetch dữ liệu từ API: " + e.getMessage());
        }
    }

    private Book convertToBook(GutendexBook gutendexBook) {
        Book book = new Book();
        book.setId(String.valueOf(gutendexBook.getId()));
        book.setTitle(gutendexBook.getTitle());

        // Convert authors
        if (gutendexBook.getAuthors() != null) {
            List<String> authorNames = gutendexBook.getAuthors().stream()
                    .map(author -> author.getName())
                    .collect(Collectors.toList());
            book.setAuthors(authorNames);
        }

        book.setSubjects(gutendexBook.getSubjects());
        book.setBookshelves(gutendexBook.getBookshelves());
        book.setLanguages(gutendexBook.getLanguages());
        book.setDownloadCount(String.valueOf(gutendexBook.getDownloadCount()));
        book.setMediaType(gutendexBook.getMediaType());

        // Get download URL from formats
        if (gutendexBook.getFormats() != null) {
            String downloadUrl = gutendexBook.getFormats().getPlainText();
            if (downloadUrl == null) {
                downloadUrl = gutendexBook.getFormats().getPlainTextAscii();
            }
            if (downloadUrl == null) {
                downloadUrl = gutendexBook.getFormats().getHtml();
            }
            book.setDownloadUrl(downloadUrl);
        }

        return book;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Book getBookById(String id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sách", "id", id));
    }

    public Book updateBookById(String id, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                updatedBook.setId(id);
                books.set(i, updatedBook);
                return updatedBook;
            }
        }
        throw new ResourceNotFoundException("Sách", "id", id);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword không được để trống");
        }

        String lowerKeyword = keyword.toLowerCase();

        return books.stream()
                .filter(book -> (book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerKeyword)) ||
                        (book.getAuthors() != null && book.getAuthors().stream()
                                .anyMatch(author -> author.toLowerCase().contains(lowerKeyword))))
                .collect(Collectors.toList());
    }

    public boolean deleteBookById(String id) {
        // Check if the book exists
        getBookById(id); // Will throw ResourceNotFoundException if not found
        books.removeIf(b -> b.getId().equals(id));
        return true;
    }

    public Book addBook(Book book) {
        // check if book already exists
        if (books.stream().anyMatch(b -> b.getId().equals(book.getId()))) {
            throw new IllegalArgumentException("Sách với ID " + book.getId() + " đã tồn tại");
        }
        books.add(book);
        return book;
    }

    public int getBookCount() {
        return books.size();
    }

    public void clearAllBooks() {
        books.clear();
    }

    /**
     * Get books by author and pagination
     */
    public List<Book> getBooks(String author, int page, int size) {
        return books.stream()
                .filter(book -> author == null || author.isEmpty()
                        || (book.getAuthors() != null && book.getAuthors().stream()
                                .anyMatch(a -> a.toLowerCase().contains(author.toLowerCase()))))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}