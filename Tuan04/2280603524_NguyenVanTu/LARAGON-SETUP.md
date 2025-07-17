# HƯỚNG DẪN KẾT NỐI MYSQL VỚI LARAGON

## 🔧 THIẾT LẬP BAN ĐẦU

### 1. Cài đặt Laragon
- Tải Laragon từ: https://laragon.org/download/
- Cài đặt và khởi động Laragon
- Đảm bảo MySQL service đang chạy (màu xanh)

### 2. Kiểm tra MySQL
1. Mở Laragon
2. Click "Database" → "Open"
3. Hoặc truy cập phpMyAdmin: http://localhost/phpmyadmin
4. Thông tin kết nối:
   ```
   Host: localhost
   Port: 3306
   Username: root
   Password: (để trống)
   ```

## 🚀 CHẠY ỨNG DỤNG VỚI MYSQL

### Phương pháp 1: Sử dụng VS Code
1. Mở Terminal trong VS Code (Ctrl + `)
2. Chạy lệnh:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=mysql
   ```

### Phương pháp 2: Sử dụng Task
1. Bấm `Ctrl + Shift + P`
2. Gõ `Tasks: Run Task`
3. Chọn `Run with MySQL (Laragon)`

### Phương pháp 3: Chạy file batch
1. Double-click file `run-mysql.bat`

## 📋 CẤU HÌNH CHI TIẾT

### File application-mysql.properties:
```properties
# MySQL Database Configuration for Laragon
spring.datasource.url=jdbc:mysql://localhost:3306/student_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=

# JPA Configuration for MySQL
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.format-sql=true
```

### Dữ liệu mẫu (data.sql):
- 4 khoa: CNTT, QTKD, Kế toán, Ngoại ngữ
- 11 môn học
- 9 lớp học
- 10 sinh viên
- Nhiều bản ghi đăng ký học

## 🔍 KIỂM TRA HOẠT ĐỘNG

### 1. Kiểm tra database:
- Mở phpMyAdmin
- Tìm database `student_management`
- Kiểm tra các bảng: faculties, subjects, student_classes, students, enrollments

### 2. Kiểm tra ứng dụng:
- Truy cập: http://localhost:8080
- Kiểm tra giao diện tree view
- Test các chức năng CRUD

## ❗ XỬ LÝ LỖI

### Lỗi kết nối MySQL:
```
Error: Could not connect to MySQL
```
**Giải pháp:**
1. Kiểm tra Laragon đã khởi động MySQL chưa
2. Restart MySQL service trong Laragon
3. Kiểm tra port 3306 có bị block không

### Lỗi timezone:
```
Error: The server time zone value is unrecognized
```
**Giải pháp:** Đã thêm `serverTimezone=Asia/Ho_Chi_Minh`

### Lỗi SSL:
```
Error: SSL connection error
```
**Giải pháp:** Đã thêm `useSSL=false`

### Lỗi tạo database:
```
Error: Unknown database 'student_management'
```
**Giải pháp:** Đã thêm `createDatabaseIfNotExist=true`

## 🎯 DEMO FEATURES

### 1. Tree View (Trang chủ):
- Hiển thị cấu trúc: Khoa → Môn học → Lớp → Sinh viên
- Giao diện theo thiết kế đã cung cấp

### 2. Quản lý Enrollment:
- Chọn lớp học
- Hiển thị sinh viên đã đăng ký / chưa đăng ký
- Thêm/xóa sinh viên khỏi lớp

### 3. CRUD Operations:
- Quản lý Khoa: /faculties
- Quản lý Môn học: /subjects  
- Quản lý Lớp học: /classes
- Quản lý Sinh viên: /students

## 📝 TESTING CHECKLIST

- [ ] Laragon MySQL đang chạy
- [ ] Database `student_management` được tạo
- [ ] Dữ liệu mẫu được load
- [ ] Ứng dụng chạy trên port 8080
- [ ] Tree view hiển thị đúng cấu trúc
- [ ] Enrollment management hoạt động
- [ ] Tất cả CRUD operations hoạt động
