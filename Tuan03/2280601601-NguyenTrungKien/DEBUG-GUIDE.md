# Hướng Dẫn Debug Lỗi Whitelabel Error Page

## Vấn đề hiện tại
Bạn đang gặp lỗi "Whitelabel Error Page" với status 500 khi truy cập một số endpoint.

## Các bước debug

### 1. Chạy ứng dụng với debug logging
```bash
# Chạy script debug
run-with-debug.bat
```

### 2. Test các endpoint
```bash
# Test tất cả endpoint
test-all-endpoints.bat
```

### 3. Kiểm tra log
Khi chạy với debug logging, bạn sẽ thấy:
- Các request đến controller
- Lỗi trong service
- Lỗi trong template rendering

### 4. Các endpoint cần test

#### API Endpoints (nên hoạt động):
- `/test-simple` - Trả về text đơn giản
- `/health` - Health check
- `/debug` - Debug endpoint
- `/test-books-simple` - Test books API

#### Web Pages (có thể gặp lỗi):
- `/test-page` - Test page với template
- `/debug-page` - Debug page với template
- `/books` - Books page
- `/` - Home page (Todo)
- `/home` - Home page
- `/about` - About page

### 5. Nguyên nhân có thể gây lỗi

#### A. Lỗi trong Service
- Null pointer exception
- Lỗi trong BookService.getAllBooks()
- Lỗi trong TodoService.getAllItems()

#### B. Lỗi trong Template
- Template không tồn tại
- Lỗi syntax trong Thymeleaf
- Attribute không được truyền đúng

#### C. Lỗi trong Controller
- Exception không được handle
- Model attribute null

### 6. Cách sửa lỗi

#### Nếu lỗi ở Service:
1. Kiểm tra log để xem exception cụ thể
2. Thêm null check trong service
3. Đảm bảo data được khởi tạo đúng

#### Nếu lỗi ở Template:
1. Kiểm tra file template có tồn tại không
2. Kiểm tra syntax Thymeleaf
3. Đảm bảo attribute được truyền đúng

#### Nếu lỗi ở Controller:
1. Thêm try-catch block
2. Log exception chi tiết
3. Return error page thay vì crash

### 7. Test từng bước

1. **Test API endpoints trước** - chúng đơn giản hơn
2. **Test web pages sau** - chúng phức tạp hơn
3. **Kiểm tra log** sau mỗi test
4. **Sửa lỗi** từng cái một

### 8. Các file quan trọng cần kiểm tra

- `BookService.java` - Service cho books
- `TodoService.java` - Service cho todos
- `BookWebController.java` - Controller cho books web
- `TodoController.java` - Controller cho todos
- `books.html` - Template cho books
- `index.html` - Template cho home

### 9. Lệnh debug hữu ích

```bash
# Xem log real-time
tail -f logs/application.log

# Test endpoint cụ thể
curl -v http://localhost:8080/books

# Kiểm tra status code
curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/books
```

### 10. Kết quả mong đợi

Sau khi debug thành công:
- Tất cả API endpoints trả về 200 OK
- Tất cả web pages load được HTML
- Không còn Whitelabel Error Page
- Log không có exception

## Liên hệ
Nếu vẫn gặp vấn đề, hãy:
1. Chạy `test-all-endpoints.bat`
2. Copy log lỗi
3. Gửi kết quả test 