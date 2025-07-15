@echo off
echo ========================================
echo Quick Test - Fixed Endpoints
echo ========================================
echo.

echo Step 1: Building project...
call mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)
echo Build successful!

echo.
echo Step 2: Starting application...
echo Application will start on http://localhost:8080
echo.
echo Step 3: Test these URLs in order:
echo.
echo === WORKING ENDPOINTS ===
echo 1. Health: http://localhost:8080/health
echo 2. Test API: http://localhost:8080/test-simple
echo 3. Test About: http://localhost:8080/test-about
echo.
echo === FIXED ENDPOINTS ===
echo 4. Test Page: http://localhost:8080/test-page (FIXED)
echo 5. Home Page: http://localhost:8080/
echo 6. Books Page: http://localhost:8080/books
echo 7. About Page: http://localhost:8080/about
echo.
echo Press Ctrl+C to stop the application
echo.

call mvnw.cmd spring-boot:run

pause 