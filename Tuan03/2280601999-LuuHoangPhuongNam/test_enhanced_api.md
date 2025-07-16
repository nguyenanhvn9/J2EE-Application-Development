# Enhanced Book API Testing Guide

## Bài thực hành 2: Hoàn thiện Logic cho BookService

### Các tính năng đã được thêm:

✅ **1. Kiểm tra Trùng lặp ID khi Thêm mới**  
✅ **2. Xử lý logic Cập nhật và Xóa cho các ID không tồn tại**  
✅ **3. Xác thực Dữ liệu đầu vào**  
✅ **4. Xây dựng chức năng Tìm kiếm Nâng cao**  

---

## API Endpoints Mới

### 1. CRUD Operations

#### 1.1 Thêm sách mới
```bash
POST /api/books
Content-Type: application/json

{
  "title": "Test Book",
  "author": "Test Author",
  "language": "English",
  "downloadCount": "100",
  "subjects": "Fiction",
  "bookshelves": "General",
  "formats": "text_plain: http://example.com",
  "gutenbergId": "12345"
}
```

#### 1.2 Lấy sách theo ID
```bash
GET /api/books/{id}
```

#### 1.3 Cập nhật sách
```bash
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Updated Book Title",
  "author": "Updated Author",
  "language": "English",
  "downloadCount": "150",
  "subjects": "Updated Fiction",
  "bookshelves": "Updated General",
  "formats": "text_plain: http://example.com/updated",
  "gutenbergId": "12345"
}
```

#### 1.4 Xóa sách
```bash
DELETE /api/books/{id}
```

### 2. Tìm kiếm

#### 2.1 Tìm kiếm theo từ khóa
```bash
GET /api/books/search?keyword=pride
```

---

## Test Cases

### Test 1: Thêm sách mới thành công
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Pride and Prejudice",
    "author": "Jane Austen",
    "language": "English",
    "downloadCount": "1000",
    "subjects": "Romance, Fiction",
    "bookshelves": "Classic Literature",
    "formats": "text_plain: http://example.com/pride.txt",
    "gutenbergId": "1342"
  }'
```
**Expected**: `201 Created` với message "Book added successfully"

### Test 2: Thêm sách trùng ID (sẽ fail)
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "title": "Duplicate Book",
    "author": "Duplicate Author",
    "language": "English",
    "downloadCount": "100",
    "subjects": "Fiction",
    "bookshelves": "General",
    "formats": "text_plain: http://example.com",
    "gutenbergId": "1342"
  }'
```
**Expected**: `400 Bad Request` với message "Book with this ID already exists."

### Test 3: Thêm sách với dữ liệu không hợp lệ
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "author": "",
    "language": "English",
    "downloadCount": "100",
    "subjects": "Fiction",
    "bookshelves": "General",
    "formats": "text_plain: http://example.com",
    "gutenbergId": "12346"
  }'
```
**Expected**: `400 Bad Request` với message "Book title cannot be null or empty."

### Test 4: Cập nhật sách thành công
```bash
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Pride and Prejudice",
    "author": "Jane Austen",
    "language": "English",
    "downloadCount": "1500",
    "subjects": "Romance, Fiction, Classic",
    "bookshelves": "Classic Literature",
    "formats": "text_plain: http://example.com/pride_updated.txt",
    "gutenbergId": "1342"
  }'
```
**Expected**: `200 OK` với dữ liệu sách đã cập nhật

### Test 5: Cập nhật sách không tồn tại
```bash
curl -X PUT http://localhost:8080/api/books/999 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Non-existent Book",
    "author": "Unknown Author",
    "language": "English",
    "downloadCount": "100",
    "subjects": "Fiction",
    "bookshelves": "General",
    "formats": "text_plain: http://example.com",
    "gutenbergId": "99999"
  }'
```
**Expected**: `404 Not Found`

### Test 6: Xóa sách thành công
```bash
curl -X DELETE http://localhost:8080/api/books/1
```
**Expected**: `200 OK` với message "Book deleted successfully"

### Test 7: Xóa sách không tồn tại
```bash
curl -X DELETE http://localhost:8080/api/books/999
```
**Expected**: `404 Not Found`

### Test 8: Tìm kiếm sách
```bash
curl "http://localhost:8080/api/books/search?keyword=pride"
```
**Expected**: `200 OK` với danh sách sách có chứa từ "pride" trong title, author hoặc subjects

### Test 9: Tìm kiếm với từ khóa rỗng
```bash
curl "http://localhost:8080/api/books/search?keyword="
```
**Expected**: `200 OK` với tất cả sách (giống như GET /api/books)

---

## Test với Postman

### Collection JSON cho Postman:
```json
{
  "info": {
    "name": "Book API Enhanced",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Get All Books",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/books"
      }
    },
    {
      "name": "Get Book by ID",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/books/1"
      }
    },
    {
      "name": "Add New Book",
      "request": {
        "method": "POST",
        "url": "http://localhost:8080/api/books",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"title\": \"Test Book\",\n  \"author\": \"Test Author\",\n  \"language\": \"English\",\n  \"downloadCount\": \"100\",\n  \"subjects\": \"Fiction\",\n  \"bookshelves\": \"General\",\n  \"formats\": \"text_plain: http://example.com\",\n  \"gutenbergId\": \"12345\"\n}"
        }
      }
    },
    {
      "name": "Update Book",
      "request": {
        "method": "PUT",
        "url": "http://localhost:8080/api/books/1",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"title\": \"Updated Book Title\",\n  \"author\": \"Updated Author\",\n  \"language\": \"English\",\n  \"downloadCount\": \"150\",\n  \"subjects\": \"Updated Fiction\",\n  \"bookshelves\": \"Updated General\",\n  \"formats\": \"text_plain: http://example.com/updated\",\n  \"gutenbergId\": \"12345\"\n}"
        }
      }
    },
    {
      "name": "Delete Book",
      "request": {
        "method": "DELETE",
        "url": "http://localhost:8080/api/books/1"
      }
    },
    {
      "name": "Search Books",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/books/search?keyword=pride"
      }
    }
  ]
}
```

---

## Kết quả mong đợi

✅ **Validation hoạt động đúng** - Không cho phép title/author rỗng  
✅ **Duplicate check hoạt động** - Không cho phép trùng ID  
✅ **Update/Delete trả về kết quả chính xác** - true/false hoặc Optional  
✅ **Search không phân biệt hoa thường** - Tìm kiếm linh hoạt  
✅ **Error handling tốt** - HTTP status codes phù hợp  

---

## Chạy test

1. **Start application:**
   ```bash
   mvn spring-boot:run
   ```

2. **Load dữ liệu từ API:**
   ```bash
   curl -X POST http://localhost:8080/api/books/fetch
   ```

3. **Test các endpoint theo thứ tự trong test cases**

4. **Monitor console** để xem các log messages 