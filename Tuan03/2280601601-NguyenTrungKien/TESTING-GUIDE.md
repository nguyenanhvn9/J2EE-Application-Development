# 🧪 Hướng dẫn Test chi tiết - Khắc phục lỗi 500

## 🚨 Tình trạng hiện tại
- ✅ `/test-simple` hoạt động: "Hello! Spring Boot is working correctly!"
- ❌ `/` (home page) bị lỗi 500
- ❌ `/books` bị lỗi 500
- ❌ `/about` bị lỗi 500

## 🔧 Các vấn đề đã được sửa

### ✅ 1. Sửa BookWebController
- Chuyển từ field injection sang constructor injection
- Loại bỏ `@Autowired` annotation

### ✅ 2. Sửa books.html template
- Loại bỏ Thymeleaf Layout Dialect
- Sử dụng Bootstrap trực tiếp
- Thêm navigation links

### ✅ 3. Tạo SimpleTestController
- Thêm test endpoints để debug
- `/test-home`, `/test-books`, `/test-about`

## 🚀 Cách test

### Bước 1: Chạy ứng dụng
```bash
test-all-endpoints.bat
```

### Bước 2: Test theo thứ tự

#### A. Basic Tests (đã hoạt động)
1. **Health Check**: http://localhost:8080/health
   - Kết quả: "OK"

2. **Test API**: http://localhost:8080/test-simple
   - Kết quả: "Hello! Spring Boot is working correctly!"

3. **Test Page**: http://localhost:8080/test-page
   - Kết quả: HTML page với message

#### B. Endpoint Tests (mới thêm)
4. **Test Home**: http://localhost:8080/test-home
   - Kết quả: "Home endpoint is working!"

5. **Test Books**: http://localhost:8080/test-books
   - Kết quả: "Books endpoint is working!"

6. **Test About**: http://localhost:8080/test-about
   - Kết quả: "About endpoint is working!"

7. **Simple Home**: http://localhost:8080/simple-home
   - Kết quả: HTML page với message

#### C. Main Pages (cần test)
8. **Home Page**: http://localhost:8080/
   - Kết quả mong đợi: Todo list page

9. **Books Page**: http://localhost:8080/books
   - Kết quả mong đợi: Books list page

10. **About Page**: http://localhost:8080/about
    - Kết quả mong đợi: About page

## 🔍 Nếu vẫn gặp lỗi

### Lỗi 500 trên Home Page (/)
**Nguyên nhân có thể:**
- TodoService không được inject đúng
- Template index.html có vấn đề
- TodoItem model có vấn đề

**Cách debug:**
1. Kiểm tra console logs
2. Test `/test-home` endpoint
3. Kiểm tra TodoController constructor

### Lỗi 500 trên Books Page (/books)
**Nguyên nhân có thể:**
- BookService không được inject đúng
- Template books.html có vấn đề
- Book model có vấn đề

**Cách debug:**
1. Kiểm tra console logs
2. Test `/test-books` endpoint
3. Kiểm tra BookWebController constructor

### Lỗi 500 trên About Page (/about)
**Nguyên nhân có thể:**
- HomeController có vấn đề
- Template about.html có vấn đề

**Cách debug:**
1. Kiểm tra console logs
2. Test `/test-about` endpoint
3. Kiểm tra HomeController

## 📋 Kết quả mong đợi

Sau khi test, bạn sẽ thấy:

### ✅ Thành công:
- Tất cả test endpoints hoạt động
- Main pages hiển thị đúng
- Không còn lỗi 500

### ❌ Nếu vẫn lỗi:
- Chia sẻ console logs
- Chia sẻ kết quả test từng endpoint
- Chia sẻ stack trace nếu có

## 🎯 Bước tiếp theo

1. **Chạy**: `test-all-endpoints.bat`
2. **Test từng endpoint** theo thứ tự
3. **Báo cáo kết quả** cho từng URL
4. **Chia sẻ logs** nếu có lỗi

---
**Lưu ý**: Hướng dẫn này sẽ giúp xác định chính xác endpoint nào bị lỗi và nguyên nhân! 