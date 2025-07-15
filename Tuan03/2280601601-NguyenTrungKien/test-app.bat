@echo off
echo ========================================
echo Testing Spring Boot Application
echo ========================================
echo.

echo Step 1: Cleaning previous builds...
if exist target rmdir /s /q target
echo Cleaned target directory.

echo.
echo Step 2: Building project...
call mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ERROR: Build failed! Please check the errors above.
    pause
    exit /b 1
)
echo Build successful!

echo.
echo Step 3: Running the application...
echo Starting Spring Boot application on http://localhost:8080
echo.
echo Test URLs (open in browser):
echo - Health Check: http://localhost:8080/health
echo - Test API: http://localhost:8080/test-simple
echo - Test Page: http://localhost:8080/test-page
echo - Home Page: http://localhost:8080/
echo.
echo Press Ctrl+C to stop the application
echo.

call mvnw.cmd spring-boot:run

pause 