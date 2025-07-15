# Ứng dụng Quản lý Sách và To-Do List

## Thông tin dự án
- **MSSV:** 2280601601
- **Họ tên:** Nguyễn Trung Kiên
- **Môn học:** COS141 - Phát triển ứng dụng với J2EE
- **Tuần:** 3

## Công nghệ sử dụng
- **Backend:** Spring Boot 3.5.3
- **Frontend:** Thymeleaf, Bootstrap 5, Font Awesome
- **Java:** 21
- **Build Tool:** Maven

## Chức năng chính

### 1. Quản lý Sách
- ✅ Xem danh sách sách
- ✅ Thêm sách mới
- ✅ Sửa thông tin sách
- ✅ Xóa sách
- ✅ Giao diện đẹp với Bootstrap

### 2. To-Do List (Bài thực hành tuần 3)
- ✅ Thêm công việc mới
- ✅ Đánh dấu hoàn thành/chưa hoàn thành
- ✅ Xóa công việc
- ✅ Lọc công việc (Tất cả/Đang làm/Hoàn thành)
- ✅ Hiển thị số lượng công việc còn lại
- ✅ Xóa tất cả công việc đã hoàn thành
- ✅ Thống kê chi tiết

## Cách chạy ứng dụng

### Cách 1: Sử dụng IDE (Khuyến nghị)
1. Mở **IntelliJ IDEA** hoặc **Eclipse**
2. Import project Maven
3. Tìm file: `QLySachJ2EeApplication.java`
4. Click chuột phải → Run

### Cách 2: Sử dụng Maven
```bash
cd Tuan03/2280601601-NguyenTrungKien
mvn spring-boot:run
```

## Đường link truy cập

### To-Do List (Trang chính)
- **http://localhost:8080/** - Trang To-Do List
- **http://localhost:8080/todos** - Chuyển hướng đến To-Do List

### Quản lý Sách
- **http://localhost:8080/books** - Danh sách sách
- **http://localhost:8080/books/add** - Thêm sách mới
- **http://localhost:8080/books/edit/{id}** - Sửa sách

### Trang khác
- **http://localhost:8080/home** - Trang chủ
- **http://localhost:8080/about** - Thông tin về ứng dụng

## Cấu trúc dự án

```
src/main/java/com/example/QLySach_J2EE/
├── controller/
│   ├── BookController.java          # API REST cho sách
│   ├── BookWebController.java       # Controller web cho sách
│   ├── HomeController.java          # Controller trang chủ
│   ├── TodoController.java          # Controller To-Do List
│   └── TestController.java          # Controller test
├── model/
│   ├── Book.java                    # Model sách
│   └── TodoItem.java                # Model công việc
├── service/
│   ├── BookService.java             # Service quản lý sách
│   └── TodoService.java             # Service quản lý To-Do
└── QLySachJ2EeApplication.java      # Main class

src/main/resources/templates/
├── layout.html                      # Layout chung
├── index.html                       # Trang To-Do List
├── books.html                       # Danh sách sách
├── add-book.html                    # Thêm sách
├── edit-book.html                   # Sửa sách
├── home.html                        # Trang chủ
├── about.html                       # Trang About
├── error.html                       # Trang lỗi
├── test.html                        # Trang test
└── simple.html                      # Trang đơn giản
```

## Tính năng nổi bật

### To-Do List
- **Giao diện đẹp:** Sử dụng Bootstrap và CSS animations
- **Tương tác mượt mà:** Checkbox tự động submit form
- **Bộ lọc thông minh:** Lọc theo trạng thái công việc
- **Thống kê real-time:** Hiển thị số lượng công việc
- **Responsive:** Hoạt động tốt trên mobile

### Quản lý Sách
- **CRUD đầy đủ:** Create, Read, Update, Delete
- **Giao diện Bootstrap:** Đẹp và dễ sử dụng
- **Validation:** Kiểm tra dữ liệu đầu vào
- **Layout template:** Sử dụng Thymeleaf Layout Dialect

## Dữ liệu mẫu

### Sách
- ID: 1, Tiêu đề: "Phat trien Ung dung voi J2EE", Tác giả: "Nguyễn Huy Cường"

### To-Do List
- "Học Spring Boot"
- "Làm bài tập Thymeleaf"
- "Ôn tập J2EE"

## Yêu cầu hệ thống
- Java 21+
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## Tác giả
**Nguyễn Trung Kiên - 2280601601** 