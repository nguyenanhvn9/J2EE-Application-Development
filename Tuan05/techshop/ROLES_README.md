# Hệ thống Roles trong TechShop

## Các Role hiện có

### 1. USER (Người dùng)
- **Quyền hạn**: Cơ bản
- **Chức năng**: 
  - Xem sản phẩm
  - Thêm vào giỏ hàng
  - Đặt hàng
  - Xem lịch sử đơn hàng
  - Quản lý profile

### 2. LEADER (Trưởng nhóm)
- **Quyền hạn**: Trung bình
- **Chức năng**: 
  - Tất cả quyền của USER
  - Xem và sửa đơn hàng
  - Quản lý mã giảm giá
  - Truy cập dashboard

### 3. MANAGER (Quản lý)
- **Quyền hạn**: Cao
- **Chức năng**: 
  - Tất cả quyền của LEADER
  - Xem, thêm, sửa sản phẩm
  - Xem và sửa đơn hàng
  - Quản lý mã giảm giá

### 4. ADMIN (Quản trị viên)
- **Quyền hạn**: Tối cao
- **Chức năng**: 
  - Tất cả quyền của MANAGER
  - Quản lý người dùng (thay đổi role, kích hoạt/vô hiệu hóa)
  - Quản lý danh mục
  - Xóa sản phẩm
  - Toàn quyền truy cập hệ thống

## Cấu trúc phân quyền

```
ADMIN > MANAGER > LEADER > USER
```

## Bảng phân quyền chi tiết

| Chức năng | URL Pattern | Vai trò được phép |
|-----------|-------------|-------------------|
| Quản lý Người dùng | `/admin/users/**` | Chỉ ADMIN |
| Quản lý Danh mục | `/admin/categories/**` | Chỉ ADMIN |
| Xem, Thêm, Sửa Sản phẩm | `/admin/products`, `/admin/products/add`, `/admin/products/edit/**` | ADMIN, MANAGER |
| Xóa Sản phẩm | `/admin/products/delete/**` | Chỉ ADMIN |
| Xem, Sửa Đơn hàng | `/admin/orders`, `/admin/orders/edit/**` | ADMIN, MANAGER, LEADER |
| Quản lý Mã giảm giá | `/admin/vouchers/**` | ADMIN, MANAGER, LEADER |

## Cách sử dụng

### Trong Controller
```java
@Autowired
private UserService userService;

// Kiểm tra role
if (user.getRole() == Role.ADMIN) {
    // Logic cho admin
}
```

### Trong Security Config
```java
.requestMatchers("/admin/users/**").hasRole("ADMIN")
.requestMatchers("/admin/products/**").hasAnyRole("ADMIN", "MANAGER")
```

### Trong Template
```html
<!-- Chỉ ADMIN mới thấy form thay đổi role -->
<div th:if="${#authorization.expression('hasRole(''ADMIN'')')}">
    <!-- Form thay đổi role -->
</div>

<!-- Chỉ ADMIN và MANAGER mới thấy menu Sản phẩm -->
<li th:if="${#authorization.expression('hasAnyRole(''ADMIN'', ''MANAGER'')')}">
    <a href="/admin/products">Sản phẩm</a>
</li>
```

## Database Schema

Role được lưu trữ dưới dạng ENUM trong database:
- `USER`
- `MANAGER` 
- `LEADER`
- `ADMIN`

## Migration

Khi chạy ứng dụng, Hibernate sẽ tự động cập nhật schema database để hỗ trợ enum Role mới. 