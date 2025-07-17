# 🎓 HỆ THỐNG QUẢN LÝ SINH VIÊN

## 📖 TỔNG QUAN DỰ ÁN

Đây là hệ thống quản lý sinh viên hoàn chỉnh được xây dựng bằng **Spring Boot**, **JPA**, **Thymeleaf** và **MySQL/H2 Database**.

### 🎯 Tính năng chính:
- ✅ Giao diện Tree View hiển thị cấu trúc: Khoa → Môn học → Lớp → Sinh viên
- ✅ Quản lý CRUD đầy đủ cho tất cả entities
- ✅ Hệ thống đăng ký sinh viên vào lớp học (Many-to-Many relationship)
- ✅ Giao diện responsive với Bootstrap
- ✅ Hỗ trợ cả H2 (development) và MySQL (production)

## 🏗️ KIẾN TRÚC HỆ THỐNG

### Database Schema:
```
Faculty (1) ——————→ (N) Subject (1) ——————→ (N) StudentClass
                                                    ↑
                                                    │ (N)
                                                    │
                                              Enrollment
                                                    │ (N)
                                                    ↓
                                                Student
```

### Tech Stack:
- **Backend:** Spring Boot 3.5.3, Spring Data JPA
- **Frontend:** Thymeleaf, Bootstrap 5, jQuery
- **Database:** H2 (dev), MySQL (prod)
- **Build Tool:** Maven
- **Java Version:** 21

## 📁 CẤU TRÚC DỰ ÁN

```
src/
├── main/
│   ├── java/com/example/__NguyenVanTu/
│   │   ├── model/           # Entity classes
│   │   │   ├── Faculty.java
│   │   │   ├── Subject.java
│   │   │   ├── StudentClass.java
│   │   │   ├── Student.java
│   │   │   └── Enrollment.java
│   │   ├── repository/      # JPA Repositories
│   │   ├── service/         # Business Logic
│   │   └── controller/      # Web Controllers
│   └── resources/
│       ├── templates/       # Thymeleaf templates
│       ├── static/         # CSS, JS, Images
│       ├── application.properties          # H2 config
│       ├── application-mysql.properties    # MySQL config
│       └── data.sql        # Sample data
├── run-mysql.bat          # Windows batch script
├── run-mysql.ps1          # PowerShell script
├── LARAGON-SETUP.md       # Detailed setup guide
└── README-MYSQL.md        # MySQL configuration guide
```

## 🚀 CÁCH CHẠY ỨNG DỤNG

### Option 1: H2 Database (Recommended for testing)
```bash
mvn spring-boot:run
```

### Option 2: MySQL với Laragon
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

### Option 3: VS Code Tasks
- `Ctrl + Shift + P` → `Tasks: Run Task`
- Chọn task phù hợp

## 🌐 GIAO DIỆN WEB

### 1. Trang chủ (Tree View) - `/`
Hiển thị cấu trúc phân cấp của toàn bộ hệ thống giáo dục:
- Dropdown chọn sinh viên
- Tree structure với expand/collapse
- Navigation đến các trang quản lý

### 2. Quản lý đăng ký học - `/enrollments`
- Chọn lớp học từ danh sách
- Hiển thị 2 cột: "Các lớp học hiện có" và "Các lớp đã đăng ký"
- Nút di chuyển sinh viên giữa 2 cột
- Các nút "Lưu thay đổi" và "Quay lại"

### 3. Các trang CRUD:
- `/faculties` - Quản lý khoa
- `/subjects` - Quản lý môn học
- `/classes` - Quản lý lớp học  
- `/students` - Quản lý sinh viên

## 🔧 CẤU HÌNH DATABASE

### H2 Database (Default):
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true  # Truy cập: http://localhost:8080/h2-console
```

### MySQL (Laragon):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh
spring.datasource.username=root
spring.datasource.password=
```

## 📊 DỮ LIỆU MẪU

Hệ thống tự động khởi tạo dữ liệu mẫu từ file `data.sql`:
- **4 Khoa:** CNTT, QTKD, Kế toán, Ngoại ngữ
- **11 Môn học:** Java, Database, Web Development, etc.
- **9 Lớp học:** 21CTT1, 21CTT2, CSDL_T2T4, etc.
- **10 Sinh viên:** Với email và thông tin đầy đủ
- **Multiple Enrollments:** Quan hệ Many-to-Many

## 🎨 UI/UX FEATURES

### Responsive Design:
- Bootstrap 5 framework
- Mobile-friendly interface
- Consistent styling across pages

### Interactive Elements:
- Collapsible tree structure
- Drag & drop functionality (enrollments)
- Flash messages for user feedback
- Form validation

### Color Scheme:
- Primary: Blue (#007bff)
- Success: Green (#28a745)  
- Warning: Yellow (#ffc107)
- Danger: Red (#dc3545)

## 🔍 TESTING & DEBUGGING

### H2 Console:
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa, Password: (empty)

### MySQL Debugging:
- phpMyAdmin: http://localhost/phpmyadmin
- HeidiSQL (included with Laragon)

### Application Logs:
- JPA SQL queries are logged (show-sql=true)
- Formatted SQL output (format-sql=true)

## 📈 PERFORMANCE CONSIDERATIONS

### JPA Optimizations:
- Lazy loading for relationships
- Custom queries for complex operations
- @ToString exclude to prevent infinite recursion

### Database Optimizations:
- Proper indexing on foreign keys
- Connection pooling (HikariCP default)
- Timezone configuration for MySQL

## 🛠️ DEVELOPMENT TOOLS

### VS Code Configuration:
- Tasks.json với pre-configured tasks
- Spring Boot extension support
- Auto-reload với devtools

### Maven Commands:
```bash
mvn clean compile          # Build
mvn spring-boot:run        # Run with H2
mvn spring-boot:run -Dspring-boot.run.profiles=mysql  # Run with MySQL
```

## 🚨 TROUBLESHOOTING

### Common Issues:

1. **Port 8080 already in use:**
   ```bash
   netstat -ano | findstr :8080
   taskkill /PID <PID> /F
   ```

2. **MySQL connection failed:**
   - Ensure Laragon is running
   - Check MySQL service status
   - Verify port 3306 is open

3. **Maven dependency issues:**
   ```bash
   mvn clean install -U
   ```

## 📞 SUPPORT

### Documentation:
- `LARAGON-SETUP.md` - Chi tiết setup Laragon
- `README-MYSQL.md` - Cấu hình MySQL
- Code comments trong source files

### Key Files:
- Main Application: `Application.java`
- Database Config: `application*.properties`
- Sample Data: `data.sql`
- Frontend: `templates/` directory
