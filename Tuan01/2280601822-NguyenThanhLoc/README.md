# 🏪 Hệ Thống Quản Lý Kho Hàng (Retail Inventory Management System)

> **Môn học:** Phát triển ứng dụng với J2EE  
> **Tác giả:** Nguyễn Thành Lộc - 2280601822

---

## 🚀 Giới thiệu
Đây là ứng dụng console quản lý kho hàng cho cửa hàng bán lẻ, áp dụng lập trình hướng đối tượng, Java Collections, Exception Handling và giao diện dòng lệnh thân thiện.

---

## 📂 Cấu trúc thư mục

```text
J2EE/
├── model/           # Các lớp sản phẩm (Product, ElectronicProduct, ...)
├── manager/         # Lớp quản lý kho (InventoryManager)
├── order/           # Xử lý đơn hàng, exception
├── Main.java        # Chương trình chính (CLI)
├── .gitignore       # Loại trừ file không cần thiết
└── README.md        # Tài liệu dự án
```

---

## 🛠️ Cách build & chạy

```sh
# Biên dịch toàn bộ mã nguồn
javac model/*.java manager/*.java order/*.java Main.java

# Chạy chương trình
java Main
```

> **Lưu ý:** Chỉ cần Java JDK 8+ và dòng lệnh (cmd, PowerShell, Terminal...)

---

## 📋 Chức năng chính

| STT | Chức năng                        | Mô tả ngắn |
|-----|-----------------------------------|------------|
|  1  | Thêm sản phẩm mới                 | Nhập thông tin, chọn loại sản phẩm |
|  2  | Xóa sản phẩm theo ID              | Xóa sản phẩm khỏi kho |
|  3  | Cập nhật thông tin sản phẩm       | Sửa giá, số lượng tồn kho |
|  4  | Tìm kiếm sản phẩm theo tên        | Tìm nhanh theo tên gần đúng |
|  5  | Tìm kiếm sản phẩm theo khoảng giá | Lọc sản phẩm theo giá |
|  6  | Hiển thị tất cả sản phẩm          | Xem bảng sản phẩm trong kho |
|  7  | Tạo đơn hàng                      | Đặt hàng, kiểm tra tồn kho |
|  0  | Thoát                             | Kết thúc chương trình |

---

## 💡 Hướng dẫn sử dụng nhanh

1. **Chạy chương trình** và làm theo menu hướng dẫn trên màn hình.
2. **Nhập thông tin** theo prompt, không dấu (do hạn chế console Windows).
3. **Xem kết quả** trực tiếp trên bảng console.

---

## 🌟 Gợi ý mở rộng
- Lưu/đọc dữ liệu từ file (CSV, JSON, Object...)
- Thêm unit test với JUnit
- Sử dụng Java Stream API cho tìm kiếm nâng cao
- Xây dựng giao diện đồ họa (JavaFX, Swing...)

---

## 📞 Liên hệ
- Nguyễn Thành Lộc - nguyenchidi.dev@gmail.com
- https://github.com/developerchidi

---

> **© 2025 - Hệ thống Quản lý kho hàng - J2EE** 
