@echo off
echo ========================================
echo Testing All Endpoints
echo ========================================
echo.

echo Waiting for application to start...
timeout /t 5 /nobreak >nul

echo.
echo Testing API Endpoints (should return JSON/text):
echo ------------------------------------------------

echo 1. Testing /test-simple...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/test-simple
echo.

echo 2. Testing /health...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/health
echo.

echo 3. Testing /debug...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/debug
echo.

echo 4. Testing /test-books-simple...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/test-books-simple
echo.

echo.
echo Testing Web Pages (should return HTML):
echo ---------------------------------------

echo 5. Testing /test-page...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/test-page | findstr /C:"Test Page Works" >nul && echo "✓ Page loaded successfully" || echo "✗ Page failed to load"
echo.

echo 6. Testing /debug-page...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/debug-page | findstr /C:"Debug Page" >nul && echo "✓ Page loaded successfully" || echo "✗ Page failed to load"
echo.

echo 7. Testing /books...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/books | findstr /C:"Books" >nul && echo "✓ Page loaded successfully" || echo "✗ Page failed to load"
echo.

echo 8. Testing / (home)...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/ | findstr /C:"Todo" >nul && echo "✓ Page loaded successfully" || echo "✗ Page failed to load"
echo.

echo 9. Testing /home...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/home | findstr /C:"Chào mừng" >nul && echo "✓ Page loaded successfully" || echo "✗ Page failed to load"
echo.

echo 10. Testing /about...
curl -s -w "Status: %%{http_code}\n" http://localhost:8080/about | findstr /C:"About" >nul && echo "✓ Page loaded successfully" || echo "✗ Page failed to load"
echo.

echo.
echo ========================================
echo Testing completed!
echo ========================================
echo.
echo If you see any "✗ Page failed to load" messages, 
echo those endpoints are causing Whitelabel Error Pages.
echo.
pause 