# To-Do List Application với Spring Boot và Thymeleaf

## Mô tả

Đây là một ứng dụng To-Do List đơn giản được xây dựng bằng Spring Boot và Thymeleaf theo yêu cầu của bài thực hành tuần 3.

## Sinh viên thực hiện

- **Họ tên**: Dương Thành Đạt
- **MSSV**: 2280618273
- **Lớp**: 22DTHE8

## Chức năng đã thực hiện

### Chức năng cơ bản

1. **Thêm công việc mới**: Người dùng có thể nhập công việc mới và thêm vào danh sách
2. **Đánh dấu hoàn thành**: Click vào checkbox để đánh dấu công việc đã hoàn thành
3. **Xóa công việc**: Click vào nút "×" để xóa công việc khỏi danh sách

### Chức năng mở rộng

1. **Hiển thị số lượng công việc còn lại**: Hiển thị số công việc chưa hoàn thành
2. **Lọc công việc**:
   - **All**: Hiển thị tất cả công việc
   - **Active**: Chỉ hiển thị công việc chưa hoàn thành
   - **Completed**: Chỉ hiển thị công việc đã hoàn thành
3. **Xóa tất cả công việc đã hoàn thành**: Nút "Clear completed" để xóa tất cả công việc đã hoàn thành

## Cấu trúc dự án

```
src/
├── main/
│   ├── java/
│   │   └── com/example/todolist/
│   │       ├── TodoListApplication.java          # Main application class
│   │       ├── controller/
│   │       │   └── TodoController.java           # Controller xử lý HTTP requests
│   │       ├── model/
│   │       │   └── TodoItem.java                 # Model cho todo item
│   │       └── service/
│   │           └── TodoService.java              # Service logic
│   └── resources/
│       ├── static/css/
│       │   └── style.css                         # CSS styling
│       ├── templates/
│       │   └── index.html                        # Thymeleaf template
│       └── application.properties                # Configuration
```

## Công nghệ sử dụng

- **Spring Boot 3.2.0**: Framework chính
- **Spring Web**: Xử lý HTTP requests
- **Thymeleaf**: Template engine cho view
- **Maven**: Build tool
- **Java 17**: Programming language

## Hướng dẫn chạy ứng dụng

### Yêu cầu hệ thống

- Java 17 hoặc cao hơn
- Maven 3.6+ (hoặc sử dụng Maven Wrapper đi kèm)

### Cách chạy

1. **Sử dụng Maven**:

   ```bash
   cd Tuan03/2280618273-DuongThanhDat
   mvn spring-boot:run
   ```

2. **Hoặc build và chạy JAR**:

   ```bash
   mvn clean package
   java -jar target/todolist-1.0.0.jar
   ```

3. **Truy cập ứng dụng**:
   - Mở trình duyệt và truy cập: http://localhost:8080

## Giao diện ứng dụng

Ứng dụng có giao diện đẹp mắt tương tự TodoMVC với các tính năng:

- Input field để thêm công việc mới
- Danh sách công việc với checkbox và nút xóa
- Footer với bộ lọc và thống kê
- Responsive design

## Kiến trúc ứng dụng

### Model (TodoItem)

- `id`: Mã định danh duy nhất
- `title`: Nội dung công việc
- `completed`: Trạng thái hoàn thành

### Service (TodoService)

- Quản lý danh sách todo items trong memory
- Các method: getAllItems(), addItem(), toggleCompleted(), deleteItem(), clearCompleted()
- Hỗ trợ filtering và thống kê

### Controller (TodoController)

- `GET /`: Hiển thị trang chủ với các todo items
- `POST /todos/add`: Thêm todo mới
- `POST /todos/{id}/toggle`: Đảo trạng thái hoàn thành
- `POST /todos/{id}/delete`: Xóa todo
- `POST /todos/clear-completed`: Xóa tất cả todo đã hoàn thành

### View (index.html)

- Sử dụng Thymeleaf để render dynamic content
- Form handling cho các actions
- CSS styling cho giao diện đẹp mắt

## Ghi chú

- Dữ liệu được lưu trữ trong memory, sẽ mất khi restart ứng dụng
- Ứng dụng được khởi tạo với một số todo items mẫu
- UI được thiết kế responsive, tương thích với mobile và desktop
