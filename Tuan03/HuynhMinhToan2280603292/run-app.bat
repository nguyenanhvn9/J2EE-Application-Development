@echo off
echo Dang khoi dong ung dung Spring Boot...
echo.

REM Kiem tra Java
java -version >nul 2>&1
if errorlevel 1 (
    echo Loi: Java khong duoc tim thay!
    pause
    exit /b 1
)

echo Java da duoc cai dat.
echo.

REM Thu chay voi Maven wrapper
echo Thu chay voi Maven wrapper...
call mvnw.cmd spring-boot:run

if errorlevel 1 (
    echo.
    echo Maven wrapper khong hoat dong.
    echo Vui long su dung IDE de chay ung dung.
    echo.
    echo Cach chay:
    echo 1. Mo project trong IntelliJ IDEA hoac Eclipse
    echo 2. Tim file QLySachJ2EeApplication.java
    echo 3. Click chuot phai -^> Run
    echo.
    pause
) 