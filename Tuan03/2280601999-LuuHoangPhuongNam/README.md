# Book Service with Lazy Loading - Enhanced Version

This Spring Boot application demonstrates lazy loading for a BookService that fetches data from the Gutenberg API, with enhanced business logic and validation.

## Features

- **Lazy Loading**: Books are fetched from the Gutenberg API only when needed
- **REST API**: Complete CRUD operations with proper validation
- **H2 Database**: In-memory database to store fetched books
- **DTO Mapping**: Converts JSON responses to Java objects
- **Data Validation**: Ensures data integrity with proper validation
- **Duplicate Prevention**: Prevents duplicate books by ID and Gutenberg ID
- **Advanced Search**: Case-insensitive search across title, author, and subjects
- **Error Handling**: Comprehensive error handling with appropriate HTTP status codes

## API Endpoints

### Basic Operations
```
GET /api/books                    # Get all books (triggers lazy loading)
GET /api/books/{id}               # Get book by ID
POST /api/books                   # Add new book (with validation)
PUT /api/books/{id}               # Update existing book
DELETE /api/books/{id}            # Delete book by ID
```

### Search and Management
```
GET /api/books/search?keyword=xyz # Search books by keyword
POST /api/books/fetch             # Manually trigger fetch from API
GET /api/books/status             # Check if books are loaded
DELETE /api/books/clear           # Clear all books
```

## Enhanced Business Logic

### 1. Duplicate ID Prevention
- Checks for existing book ID before adding
- Checks for existing Gutenberg ID before adding
- Throws `IllegalArgumentException` for duplicates

### 2. Data Validation
- Validates that title and author are not null or empty
- Ensures data integrity before saving
- Provides clear error messages for invalid data

### 3. Update and Delete Logic
- `updateBook()` returns `Optional<Book>` - empty if book not found
- `deleteBook()` returns `boolean` - false if book not found
- Proper HTTP status codes (404 for not found)

### 4. Advanced Search
- Case-insensitive search across multiple fields
- Searches in title, author, and subjects
- Returns all books if keyword is empty

## How to Run

1. **Start the application**:
   ```bash
   mvn spring-boot:run
   ```

2. **Access the API**:
   - Application runs on: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: `password`

3. **Test with Postman or curl**:

   **Load initial data:**
   ```bash
   curl -X POST http://localhost:8080/api/books/fetch
   ```

   **Add a new book:**
   ```bash
   curl -X POST http://localhost:8080/api/books \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Test Book",
       "author": "Test Author",
       "language": "English",
       "downloadCount": "100",
       "subjects": "Fiction",
       "bookshelves": "General",
       "formats": "text_plain: http://example.com",
       "gutenbergId": "12345"
     }'
   ```

   **Search books:**
   ```bash
   curl "http://localhost:8080/api/books/search?keyword=pride"
   ```

   **Update a book:**
   ```bash
   curl -X PUT http://localhost:8080/api/books/1 \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Updated Book Title",
       "author": "Updated Author",
       "language": "English",
       "downloadCount": "150",
       "subjects": "Updated Fiction",
       "bookshelves": "Updated General",
       "formats": "text_plain: http://example.com/updated",
       "gutenbergId": "12345"
     }'
   ```

## Project Structure

```
src/main/java/com/hutech/cos141_demo/BaiTH124/
├── config/
│   └── RestTemplateConfig.java
├── controller/
│   └── BookController.java
├── dto/
│   ├── GutenbergAuthor.java
│   ├── GutenbergBook.java
│   ├── GutenbergFormats.java
│   └── GutenbergResponse.java
├── model/
│   └── Book.java
├── repository/
│   └── BookRepository.java
└── service/
    └── BookService.java
```

## Lazy Loading Implementation

The `BookService` implements lazy loading by:
1. Only fetching data from the Gutenberg API when `getAllBooks()` is called for the first time
2. Storing the fetched data in the H2 database
3. Subsequent calls return data from the database without making API calls
4. The `isLoaded` flag tracks whether data has been fetched

## Enhanced Features

### Validation Examples
- **Valid book**: Title and author are required
- **Invalid book**: Empty title or author will throw `IllegalArgumentException`
- **Duplicate book**: Same ID or Gutenberg ID will throw `IllegalArgumentException`

### Search Examples
- `GET /api/books/search?keyword=pride` - Finds books with "pride" in title/author/subjects
- `GET /api/books/search?keyword=JANE` - Case-insensitive search for "JANE"
- `GET /api/books/search?keyword=` - Returns all books

### Error Handling
- `400 Bad Request` - Invalid data or duplicates
- `404 Not Found` - Book not found for update/delete
- `201 Created` - Book successfully added
- `200 OK` - Successful operations

## Dependencies

- Spring Boot 3.5.3
- Spring Data JPA
- Spring Web
- H2 Database
- Lombok
- RestTemplate for API calls

## Testing

See `test_enhanced_api.md` for comprehensive test cases and examples. 