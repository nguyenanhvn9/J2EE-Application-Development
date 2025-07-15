@echo off
echo ========================================
echo    BUILD VA CHAY UNG DUNG SPRING BOOT
echo ========================================
echo.

REM Kiem tra Java
echo [1/4] Kiem tra Java...
java -version >nul 2>&1
if errorlevel 1 (
    echo Loi: Java khong duoc tim thay!
    pause
    exit /b 1
)
echo ✓ Java da duoc cai dat
echo.

REM Xoa thu muc target cu
echo [2/4] Xoa thu muc target cu...
if exist target rmdir /s /q target
echo ✓ Da xoa thu muc target
echo.

REM Thu chay voi Maven wrapper
echo [3/4] Thu chay voi Maven wrapper...
call mvnw.cmd clean compile >nul 2>&1
if errorlevel 1 (
    echo Loi: Maven wrapper khong hoat dong!
    echo.
    echo ========================================
    echo    HUONG DAN CHAY BANG IDE
    echo ========================================
    echo.
    echo 1. Mo project trong IntelliJ IDEA:
    echo    - File -> Open
    echo    - Chon: D:\2280601601_NguyenTrungKien\Tuan03\2280601601-NguyenTrungKien
    echo.
    echo 2. Tim file: QLySachJ2EeApplication.java
    echo    - Duong dan: src/main/java/com/example/QLySach_J2EE/QLySachJ2EeApplication.java
    echo.
    echo 3. Click chuot phai -> Run 'QLySachJ2EeApplication'
    echo.
    echo 4. Truy cap: http://localhost:8080/books
    echo.
    pause
    exit /b 1
)

REM Chay ung dung
echo [4/4] Chay ung dung Spring Boot...
echo.
echo ✓ Ung dung dang chay tai: http://localhost:8080
echo ✓ Trang sach: http://localhost:8080/books
echo ✓ Trang them sach: http://localhost:8080/books/add
echo.
echo Nhan Ctrl+C de dung ung dung
echo.
call mvnw.cmd spring-boot:run 