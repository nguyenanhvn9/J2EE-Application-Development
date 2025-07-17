# Hướng dẫn kết nối MySQL với Laragon

## Bước 1: Cài đặt và khởi động Laragon

1. Tải và cài đặt Laragon từ: https://laragon.org/
2. Khởi động Laragon
3. Bấm "Start All" để khởi động Apache và MySQL

## Bước 2: Kiểm tra MySQL

1. Mở HeidiSQL hoặc phpMyAdmin từ Laragon
2. Kiểm tra kết nối MySQL với:
   - Host: localhost
   - Port: 3306
   - Username: root
   - Password: (để trống)

## Bước 3: Chạy ứng dụng

### Cách 1: Sử dụng VS Code Task
1. Mở VS Code
2. Bấm Ctrl+Shift+P
3. Gõ "Tasks: Run Task"
4. Chọn "Run with MySQL (Laragon)"

### Cách 2: Sử dụng file batch
1. Chạy file `run-mysql.bat`

### Cách 3: Sử dụng Maven command
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Bước 4: Truy cập ứng dụng

- URL: http://localhost:8080
- Database sẽ được tự động tạo với tên: `student_management`
- Dữ liệu mẫu sẽ được khởi tạo tự động

## Cấu hình Database

File `application-mysql.properties` có cấu hình:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh
spring.datasource.username=root
spring.datasource.password=
```

## Troubleshooting

### Lỗi kết nối database:
1. Kiểm tra Laragon đã khởi động MySQL chưa
2. Kiểm tra port 3306 có bị chiếm dụng không
3. Kiểm tra username/password MySQL

### Lỗi timezone:
- Đã thêm `serverTimezone=Asia/Ho_Chi_Minh` trong connection string

### Lỗi SSL:
- Đã thêm `useSSL=false` để disable SSL

## Cấu trúc Database

Ứng dụng sẽ tự động tạo các bảng:
- `faculties` - Quản lý khoa
- `subjects` - Quản lý môn học  
- `student_classes` - Quản lý lớp học
- `students` - Quản lý sinh viên
- `enrollments` - Quản lý đăng ký học

Dữ liệu mẫu được khởi tạo từ file `data.sql`
