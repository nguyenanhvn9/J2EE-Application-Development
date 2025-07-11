# **Quy Trình Làm Việc và Nộp Bài Qua GitHub**

### *Môn học: Phát triển ứng dụng với J2EE*

Chào mừng các bạn đến với môn học. Repository này là repository **TRUNG TÂM**, nơi chứa đề bài và cấu trúc chung.

> [\!IMPORTANT]
> Các bạn sẽ **KHÔNG** làm việc trực tiếp trên đây. Thay vào đó, hãy đọc kỹ và làm theo hướng dẫn **Fork & Pull Request** được mô tả chi tiết dưới đây.

-----

## **I. HƯỚNG DẪN THIẾT LẬP MÔI TRƯỜNG (Chỉ làm 1 lần duy nhất)**

*Hoàn thành các bước sau để chuẩn bị môi trường làm việc cho toàn bộ môn học.*

### **Bước 1: Fork Repository - Tạo "Bản Sao" về tài khoản GitHub của bạn**

1.  Tại trang repository này của giảng viên, nhấn vào nút **Fork** ở góc trên bên phải.
2.  Một cửa sổ sẽ hiện ra, nhấn **Create fork**.
3.  GitHub sẽ tự động tạo một bản sao của repository này dưới tài khoản của bạn.
      * URL của bản sao sẽ có dạng: `github.com/your-username/WinForms-Template`.
      * Đây chính là **Repository cá nhân** của bạn, nơi bạn có toàn quyền chỉnh sửa và lưu trữ bài làm.

### **Bước 2: Clone Repository cá nhân về máy tính**

1.  Truy cập trang repository bạn vừa fork (repository cá nhân).

2.  Nhấn vào nút màu xanh lá **`< > Code`** và sao chép đường link `HTTPS`.

3.  Mở **Git Bash** (hoặc Terminal) trên máy tính của bạn, di chuyển đến thư mục bạn muốn lưu trữ bài tập và chạy lệnh sau:

    ```bash
    git clone [đường-link-HTTPS-bạn-vừa-sao-chép]
    ```

### **Bước 3: Tạo Branch (Nhánh) làm việc cá nhân**
Tạo branch mới với tên theo định dạng: **[MSSV]-[HoTen]**
> [\!NOTE]
> Việc tạo Branch riêng giúp công việc của bạn được độc lập, tránh ảnh hưởng đến nhánh `main` gốc. Toàn bộ bài làm trong suốt môn học sẽ được thực hiện trên branch này.

Sau khi clone xong, hãy di chuyển vào thư mục dự án và tạo branch mới theo cú pháp `[MSSV]-[HoTen]`.

```bash
# Di chuyển vào thư mục repository vừa clone về
cd WinForms-Template

# Tạo và chuyển sang một branch mới để làm việc
git checkout -b [MSSV]-[HoTen]

# Ví dụ cụ thể:
# git checkout -b 2001210123-NguyenVanA
```

-----

## **II. QUY TRÌNH LÀM BÀI VÀ NỘP BÀI HÀNG TUẦN**

*Đây là vòng lặp công việc bạn sẽ thực hiện cho mỗi bài thực hành hàng tuần.*

### **Bước 1: Phát triển tính năng và Commit trên Branch cá nhân**

1.  **Đảm bảo** bạn đang ở trên branch của mình bằng lệnh `git branch` (branch hiện tại sẽ có dấu `*`).

2.  **Tạo project** Visual Studio vào đúng thư mục của tuần tương ứng (ví dụ: `Tuan02`), đặt tên cho Project theo định dạng **[MSSV]-[HoTen]**.

3.  **Làm bài và commit thường xuyên.** 

    ```bash
    # 1. Thêm tất cả các file đã thay đổi vào Staging Area
    git add .

    # 2. Commit với một thông điệp mô tả rõ ràng sự thay đổi
    git commit -m "Tuan02: Feat - Them chuc nang luu du lieu Sinh Vien"
    ```

### **Bước 2: Đẩy (Push) mã nguồn lên Repository cá nhân (Fork)**

Sau khi hoàn thành bài làm của tuần, hãy đẩy các commit từ máy tính của bạn lên branch làm việc trên GitHub.

  * **Đối với lần push đầu tiên của branch này:**

    ```bash
    git push --set-upstream origin [MSSV]-[HoTen]
    ```

  * **Từ những lần push sau, bạn chỉ cần dùng lệnh ngắn gọn:**

    ```bash
    git push
    ```

### **Bước 3: Nộp bài bằng cách tạo Pull Request (PR)**

Đây là bước **quan trọng nhất** để nộp bài cho giảng viên.

1.  Truy cập trang repository cá nhân của bạn trên GitHub.

2.  Bạn sẽ thấy một thông báo nổi bật với nút **"Compare & pull request"**. Hãy nhấn vào đó.

      * *Nếu không thấy, hãy vào tab **"Pull requests"** và nhấn **"New pull request"**.*

3.  **Kiểm tra lại thông tin cẩn thận:**

   > [\!IMPORTANT]
        Dòng `base repository` phải là repository của giảng viên và `head repository` là repository của bạn. Mũi tên chỉ hướng nộp bài:
        `base: main` **\<--** `compare: [MSSV]-[HoTen]`

4.  **Đặt tiêu đề cho Pull Request** theo cú pháp:

      * `[Mã sinh viên-Họ và tên] Bài thực hành tuần X (X là thứ tự của tuần)`
      * *Ví dụ:* `[2001210123-Nguyễn Văn A] Bài thực hành tuần 1`

5.  Nhấn nút **"Create pull request"**.

:white\_check\_mark: **Bạn đã nộp bài thành công\!** Giảng viên sẽ nhận được thông báo, xem bài làm và có thể để lại phản hồi trực tiếp trên Pull Request của bạn.
