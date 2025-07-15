@echo off
echo Testing all endpoints...
echo.

echo Testing /test-simple...
curl -s http://localhost:8080/test-simple
echo.
echo.

echo Testing /test-page...
curl -s http://localhost:8080/test-page
echo.
echo.

echo Testing /books...
curl -s http://localhost:8080/books
echo.
echo.

echo Testing /...
curl -s http://localhost:8080/
echo.
echo.

echo Testing /home...
curl -s http://localhost:8080/home
echo.
echo.

echo Testing /about...
curl -s http://localhost:8080/about
echo.
echo.

echo Testing /health...
curl -s http://localhost:8080/health
echo.
echo.

echo All tests completed.
pause 