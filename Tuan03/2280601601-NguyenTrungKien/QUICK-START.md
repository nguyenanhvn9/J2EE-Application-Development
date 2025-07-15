# Hướng Dẫn Nhanh - Chạy Ứng Dụng Spring Boot

## 🚀 Cách chạy ứng dụng

### Bước 1: Chạy ứng dụng
```powershell
# Mở PowerShell và chạy:
.\run-app.ps1
```

Hoặc chạy từng lệnh:
```powershell
cd Tuan03/2280601601-NguyenTrungKien
.\mvnw.cmd spring-boot:run
```

### Bước 2: Đợi ứng dụng khởi động
- Đợi thấy dòng: `Started QLySachJ2EeApplication in X.XXX seconds`
- Ứng dụng sẽ chạy tại: http://localhost:8080

### Bước 3: Test các endpoint
Mở trình duyệt và truy cập:

#### ✅ API Endpoints (nên hoạt động):
- **Health Check**: http://localhost:8080/health
- **Test Simple**: http://localhost:8080/test-simple
- **Debug API**: http://localhost:8080/debug

#### 📄 Web Pages:
- **Home Page**: http://localhost:8080/
- **Books Page**: http://localhost:8080/books
- **Test Page**: http://localhost:8080/test-page

## 🔧 Nếu gặp lỗi

### Lỗi Whitelabel Error Page:
1. **Kiểm tra log** trong terminal khi chạy ứng dụng
2. **Tìm dòng có "Exception"** hoặc "Caused by"
3. **Copy log lỗi** và gửi lại để được hỗ trợ

### Lỗi PowerShell:
- Không dùng `&&` trong PowerShell
- Chạy từng lệnh một

### Lỗi Maven:
- Đảm bảo Java đã cài đặt: `java -version`
- Kiểm tra file `mvnw.cmd` có tồn tại

## 📋 Checklist

- [ ] Ứng dụng khởi động thành công
- [ ] `/health` trả về "OK"
- [ ] `/test-simple` trả về message
- [ ] `/books` hiển thị danh sách sách
- [ ] `/` hiển thị todo list
- [ ] Không có Whitelabel Error Page

## 🆘 Nếu vẫn gặp vấn đề

1. **Chạy script test:**
   ```powershell
   .\test-endpoints-simple.ps1
   ```

2. **Kiểm tra log chi tiết** trong terminal

3. **Gửi lại thông tin:**
   - Log lỗi từ terminal
   - Kết quả test từ script
   - Screenshot Whitelabel Error Page

## 📞 Liên hệ
Nếu cần hỗ trợ thêm, hãy cung cấp:
- Log lỗi chi tiết
- Kết quả test endpoint
- Mô tả lỗi cụ thể 