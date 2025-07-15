@echo off
echo ========================================
echo Running Application and Testing
echo ========================================
echo.

cd /d "%~dp0"

echo Step 1: Building application...
call mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Starting application in background...
start /B mvnw.cmd spring-boot:run

echo.
echo Step 3: Waiting for application to start...
timeout /t 15 /nobreak >nul

echo.
echo Step 4: Testing endpoints...
echo.

echo Testing basic endpoints:
echo ------------------------

echo Testing /health...
curl -s http://localhost:8080/health
echo.

echo Testing /test-simple...
curl -s http://localhost:8080/test-simple
echo.

echo Testing /debug...
curl -s http://localhost:8080/debug
echo.

echo.
echo Testing web pages:
echo ------------------

echo Testing /test-page...
curl -s http://localhost:8080/test-page | findstr /C:"Test Page Works" >nul && echo "✓ Test page works" || echo "✗ Test page failed"
echo.

echo Testing /books...
curl -s http://localhost:8080/books | findstr /C:"Books" >nul && echo "✓ Books page works" || echo "✗ Books page failed"
echo.

echo Testing / (home)...
curl -s http://localhost:8080/ | findstr /C:"Todo" >nul && echo "✓ Home page works" || echo "✗ Home page failed"
echo.

echo.
echo ========================================
echo Testing completed!
echo ========================================
echo.
echo If you see any "✗" messages, check the application logs.
echo.
echo Application is running at: http://localhost:8080
echo Press any key to stop the application...
pause >nul

echo Stopping application...
taskkill /F /IM java.exe >nul 2>&1
echo Application stopped. 