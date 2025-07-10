@echo off
echo Starting Spring Boot Application...
echo.

REM Kiểm tra Java
java -version >nul 2>&1
if %errorlevel%  neq 0 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Chạy ứng dụng Spring Boot
echo Compiling and running the application...
echo.

REM Sử dụng Java trực tiếp để chạy
java -cp "target/classes;target/dependency/*" com.example.QLySachJ2EE.QLySachJ2EeApplication

pause 