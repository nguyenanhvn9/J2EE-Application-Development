@echo off
echo.
echo ===============================================
echo   BOOK SERVICE - MYSQL SETUP (LARAGON)
echo ===============================================
echo.

echo [1] Checking Laragon MySQL status...
netstat -an | findstr :3306 >nul
if %errorlevel%==0 (
    echo ✓ MySQL is running on port 3306
) else (
    echo ✗ MySQL is not running!
    echo Please start Laragon and make sure MySQL is running
    echo Then run this script again.
    pause
    exit /b 1
)

echo.
echo [2] Installing MySQL dependencies...
mvn dependency:resolve

echo.
echo [3] Creating database if not exists...
mysql -u root -h localhost -e "CREATE DATABASE IF NOT EXISTS bookservice_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
if %errorlevel%==0 (
    echo ✓ Database created/verified successfully
) else (
    echo ✗ Failed to create database
    echo Please check your MySQL connection
    pause
    exit /b 1
)

echo.
echo [4] Running SQL initialization script...
mysql -u root -h localhost bookservice_db < src\main\resources\init-database.sql
if %errorlevel%==0 (
    echo ✓ Database initialized successfully
) else (
    echo ✗ Failed to initialize database
)

echo.
echo [5] Starting Spring Boot application with MySQL profile...
echo Application will be available at: http://localhost:8080
echo Database console: http://localhost:8080/h2-console (if H2 fallback is needed)
echo.

mvn spring-boot:run -Dspring-boot.run.profiles=mysql

echo.
echo Application stopped.
pause
