# Ứng dụng Quản lý Sách và Người dùng với J2EE

## Mô tả
Ứng dụng Spring Boot với các tính năng:
- Quản lý sách với lazy loading từ API Gutendex
- Quản lý người dùng với lazy loading từ JSONPlaceholder API
- Phân trang và tìm kiếm
- Xử lý exception tập trung
- Caching dữ liệu

## Các tính năng đã triển khai

### Bài thực hành 1: Lazy Loading cho BookService
- ✅ Gọi API public (Gutendex) bằng RestTemplate
- ✅ Tạo các DTO để ánh xạ dữ liệu JSON
- ✅ Chuyển đổi DTO sang Model Book
- ✅ Lazy loading khi cần thiết

### Bài thực hành 2: Hoàn thiện Logic cho BookService
- ✅ Kiểm tra trùng lặp ID khi thêm mới
- ✅ Xử lý logic cập nhật và xóa cho ID không tồn tại
- ✅ Xác thực dữ liệu đầu vào
- ✅ Tìm kiếm nâng cao

### Bài thực hành 3: API Quản lý Người dùng
- ✅ Sử dụng JSONPlaceholder API
- ✅ Tạo Model User và các DTO
- ✅ UserService với lazy loading
- ✅ UserController với đầy đủ CRUD

### Bài thực hành 4: Yêu cầu Mở rộng
- ✅ Tìm kiếm và phân trang
- ✅ Xử lý ngoại lệ tập trung
- ✅ Caching dữ liệu

## API Endpoints

### Books API

#### Lấy danh sách sách
```
GET /api/books
GET /api/books?author=Paulo Coelho
GET /api/books?page=0&size=10
GET /api/books?author=George&page=0&size=5
```

#### Lấy sách theo ID
```
GET /api/books/{id}
```

#### Thêm sách mới
```
POST /api/books
Content-Type: application/json

{
  "id": 6,
  "title": "New Book",
  "author": "New Author"
}
```

#### Cập nhật sách
```
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "author": "Updated Author"
}
```

#### Xóa sách
```
DELETE /api/books/{id}
```

#### Tìm kiếm sách
```
GET /api/books/search?keyword=great
```

### Users API

#### Lấy danh sách người dùng
```
GET /api/users
```

#### Lấy người dùng theo ID
```
GET /api/users/{id}
```

#### Thêm người dùng mới
```
POST /api/users
Content-Type: application/json

{
  "id": 6,
  "name": "New User",
  "username": "newuser",
  "email": "newuser@example.com",
  "phone": "123-456-7890"
}
```

#### Cập nhật người dùng
```
PUT /api/users/{id}
Content-Type: application/json

{
  "name": "Updated Name",
  "username": "updateduser",
  "email": "updated@example.com",
  "phone": "098-765-4321"
}
```

#### Xóa người dùng
```
DELETE /api/users/{id}
```

## Cách chạy ứng dụng

1. Đảm bảo đã cài đặt Java 21 và Maven
2. Clone repository
3. Chạy lệnh: `mvn spring-boot:run`
4. Ứng dụng sẽ chạy tại: `http://localhost:8080`

## Testing với Postman

### Test Books API
1. **Lấy tất cả sách**: `GET http://localhost:8080/api/books`
2. **Lấy sách theo ID**: `GET http://localhost:8080/api/books/1`
3. **Thêm sách mới**: `POST http://localhost:8080/api/books`
4. **Cập nhật sách**: `PUT http://localhost:8080/api/books/1`
5. **Xóa sách**: `DELETE http://localhost:8080/api/books/1`
6. **Tìm kiếm**: `GET http://localhost:8080/api/books/search?keyword=gatsby`

### Test Users API
1. **Lấy tất cả người dùng**: `GET http://localhost:8080/api/users`
2. **Lấy người dùng theo ID**: `GET http://localhost:8080/api/users/1`
3. **Thêm người dùng mới**: `POST http://localhost:8080/api/users`
4. **Cập nhật người dùng**: `PUT http://localhost:8080/api/users/1`
5. **Xóa người dùng**: `DELETE http://localhost:8080/api/users/1`

## Tính năng nâng cao

### Phân trang và lọc
- Lọc theo tác giả: `?author=George`
- Phân trang: `?page=0&size=10`
- Kết hợp: `?author=George&page=0&size=5`

### Exception Handling
- 404 Not Found cho ID không tồn tại
- 400 Bad Request cho dữ liệu không hợp lệ
- Thông báo lỗi JSON rõ ràng

### Caching
- Cache cho danh sách sách và người dùng
- Cache cho tìm kiếm
- Tự động xóa cache khi có thay đổi

## Cấu trúc Project

```
src/main/java/com/example/QLySachJ2EE/
├── config/
│   └── RestTemplateConfig.java
├── controller/
│   ├── BookController.java
│   └── UserController.java
├── dto/
│   ├── GutendexBookDTO.java
│   ├── GutendexResponseDTO.java
│   ├── GutendexAuthorDTO.java
│   ├── GutendexFormatDTO.java
│   ├── JsonPlaceholderUserDTO.java
│   ├── JsonPlaceholderAddressDTO.java
│   ├── JsonPlaceholderGeoDTO.java
│   └── JsonPlaceholderCompanyDTO.java
├── exception/
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
├── model/
│   ├── Book.java
│   └── User.java
├── service/
│   ├── BookService.java
│   └── UserService.java
└── QLySachJ2EeApplication.java
``` 