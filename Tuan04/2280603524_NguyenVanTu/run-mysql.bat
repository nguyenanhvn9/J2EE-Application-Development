@echo off
echo Starting Spring Boot Application with MySQL (Laragon)...
echo Make sure Laragon is running with MySQL service started
echo.

mvn spring-boot:run -Dspring-boot.run.profiles=mysql

pause
