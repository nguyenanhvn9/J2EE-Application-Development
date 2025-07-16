# Advanced Features Testing Guide

## Bài thực hành 4: Yêu cầu Mở rộng

### Các tính năng đã được triển khai:

✅ **1. Tìm kiếm và Phân trang (Filtering & Pagination)**  
✅ **2. Xử lý Ngoại lệ (Exception Handling)**  
✅ **3. Mở rộng về Caching Dữ liệu**  

---

## 1. Pagination và Filtering

### 1.1 Pagination cơ bản
```bash
# Lấy trang đầu tiên với 5 sách
curl "http://localhost:8080/api/books?page=0&size=5"

# Lấy trang thứ 2 với 10 sách
curl "http://localhost:8080/api/books?page=1&size=10"
```

### 1.2 Filtering theo tác giả
```bash
# Lọc sách theo tác giả
curl "http://localhost:8080/api/books?author=Jane%20Austen"

# Kết hợp pagination và filtering
curl "http://localhost:8080/api/books?author=Jane%20Austen&page=0&size=5"
```

### 1.3 Response format cho pagination
```json
{
  "content": [
    {
      "id": 1,
      "title": "Pride and Prejudice",
      "author": "Jane Austen",
      "language": "English",
      "downloadCount": "1000",
      "subjects": "Romance, Fiction",
      "bookshelves": "Classic Literature",
      "formats": "text_plain: http://example.com/pride.txt",
      "gutenbergId": "1342"
    }
  ],
  "page": 0,
  "size": 5,
  "totalElements": 32,
  "totalPages": 7,
  "hasNext": true,
  "hasPrevious": false
}
```

---

## 2. Exception Handling

### 2.1 ResourceNotFoundException
```bash
# Tìm sách không tồn tại
curl http://localhost:8080/api/books/9999
```

**Expected Response:**
```json
{
  "status": 404,
  "message": "Book not found with id : '9999'",
  "path": "/api/books/9999",
  "timestamp": "2024-01-15T10:30:00",
  "details": {}
}
```

### 2.2 IllegalArgumentException
```bash
# Thêm sách với dữ liệu không hợp lệ
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "author": "",
    "language": "English"
  }'
```

**Expected Response:**
```json
{
  "status": 400,
  "message": "Book title cannot be null or empty.",
  "path": "/api/books",
  "timestamp": "2024-01-15T10:30:00",
  "details": {}
}
```

### 2.3 ApiException
```bash
# Test khi API external bị lỗi (có thể test bằng cách tắt internet)
curl -X POST http://localhost:8080/api/books/fetch
```

**Expected Response:**
```json
{
  "status": 500,
  "message": "Error fetching books from API: Connection refused",
  "path": "/api/books/fetch",
  "timestamp": "2024-01-15T10:30:00",
  "details": {}
}
```

### 2.4 Page out of range
```bash
# Yêu cầu trang không tồn tại
curl "http://localhost:8080/api/books?page=999&size=10"
```

**Expected Response:**
```json
{
  "status": 400,
  "message": "Page number 999 is out of range. Total pages: 7",
  "path": "/api/books",
  "timestamp": "2024-01-15T10:30:00",
  "details": {}
}
```

---

## 3. Caching

### 3.1 Test caching với search
```bash
# Lần đầu tiên - sẽ query database
curl "http://localhost:8080/api/books/search?keyword=pride"

# Lần thứ hai - sẽ lấy từ cache (nhanh hơn)
curl "http://localhost:8080/api/books/search?keyword=pride"
```

### 3.2 Test caching với pagination
```bash
# Lần đầu tiên
curl "http://localhost:8080/api/books?page=0&size=5"

# Lần thứ hai - cached
curl "http://localhost:8080/api/books?page=0&size=5"
```

### 3.3 Cache invalidation
```bash
# Clear cache bằng cách clear books
curl -X DELETE http://localhost:8080/api/books/clear

# Sau đó query lại sẽ cache mới
curl "http://localhost:8080/api/books?page=0&size=5"
```

---

## 4. Test Cases Tổng hợp

### Test Case 1: Pagination với Filtering
```bash
# 1. Load dữ liệu
curl -X POST http://localhost:8080/api/books/fetch

# 2. Test pagination cơ bản
curl "http://localhost:8080/api/books?page=0&size=3"

# 3. Test filtering
curl "http://localhost:8080/api/books?author=Charles%20Dickens"

# 4. Test kết hợp
curl "http://localhost:8080/api/books?author=Charles%20Dickens&page=0&size=2"
```

### Test Case 2: Exception Handling
```bash
# 1. Test ResourceNotFoundException
curl http://localhost:8080/api/books/99999

# 2. Test IllegalArgumentException
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title": "", "author": "Test"}'

# 3. Test page out of range
curl "http://localhost:8080/api/books?page=1000&size=10"
```

### Test Case 3: Caching Performance
```bash
# 1. Measure first request time
time curl "http://localhost:8080/api/books/search?keyword=pride"

# 2. Measure second request time (should be faster)
time curl "http://localhost:8080/api/books/search?keyword=pride"

# 3. Test different keywords (different cache keys)
curl "http://localhost:8080/api/books/search?keyword=austen"
curl "http://localhost:8080/api/books/search?keyword=romance"
```

---

## 5. Postman Collection

### Import collection vào Postman:

```json
{
  "info": {
    "name": "Book API Advanced Features",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Pagination Tests",
      "item": [
        {
          "name": "Get First Page",
          "request": {
            "method": "GET",
            "url": "http://localhost:8080/api/books?page=0&size=5"
          }
        },
        {
          "name": "Get Second Page",
          "request": {
            "method": "GET",
            "url": "http://localhost:8080/api/books?page=1&size=5"
          }
        },
        {
          "name": "Filter by Author",
          "request": {
            "method": "GET",
            "url": "http://localhost:8080/api/books?author=Jane%20Austen"
          }
        },
        {
          "name": "Filter + Pagination",
          "request": {
            "method": "GET",
            "url": "http://localhost:8080/api/books?author=Jane%20Austen&page=0&size=3"
          }
        }
      ]
    },
    {
      "name": "Exception Tests",
      "item": [
        {
          "name": "Book Not Found",
          "request": {
            "method": "GET",
            "url": "http://localhost:8080/api/books/99999"
          }
        },
        {
          "name": "Invalid Data",
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
              "raw": "{\"title\": \"\", \"author\": \"\"}"
            }
          }
        },
        {
          "name": "Page Out of Range",
          "request": {
            "method": "GET",
            "url": "http://localhost:8080/api/books?page=999&size=10"
          }
        }
      ]
    },
    {
      "name": "Caching Tests",
      "item": [
        {
          "name": "Search First Time",
          "request": {
            "method": "GET",
            "url": "http://localhost:8080/api/books/search?keyword=pride"
          }
        },
        {
          "name": "Search Second Time (Cached)",
          "request": {
            "method": "GET",
            "url": "http://localhost:8080/api/books/search?keyword=pride"
          }
        }
      ]
    }
  ]
}
```

---

## 6. Monitoring và Debug

### 6.1 Enable debug logging
Thêm vào `application.properties`:
```properties
logging.level.com.hutech.cos141_demo.BaiTH124=DEBUG
logging.level.org.springframework.cache=DEBUG
```

### 6.2 Monitor cache hits/misses
```bash
# Watch logs for cache operations
tail -f logs/application.log | grep -i cache
```

### 6.3 Performance testing
```bash
# Test response time
for i in {1..5}; do
  echo "Request $i:"
  time curl -s "http://localhost:8080/api/books/search?keyword=pride" > /dev/null
done
```

---

## 7. Expected Results

### Performance Improvements:
✅ **Pagination** - Giảm tải cho server với large datasets  
✅ **Caching** - Response time nhanh hơn cho repeated requests  
✅ **Filtering** - Tìm kiếm hiệu quả hơn  

### Error Handling:
✅ **404 Not Found** - ResourceNotFoundException  
✅ **400 Bad Request** - IllegalArgumentException  
✅ **500 Internal Server Error** - ApiException  
✅ **JSON Error Response** - Structured error messages  

### User Experience:
✅ **Consistent API** - Tất cả endpoints có cùng format  
✅ **Clear Error Messages** - Dễ debug và troubleshoot  
✅ **Performance** - Fast response times với caching  