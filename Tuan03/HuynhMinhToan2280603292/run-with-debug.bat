@echo off
echo Building and running application with debug logging...
echo.

cd /d "%~dp0"

echo Cleaning previous build...
call mvnw.cmd clean

echo Building application...
call mvnw.cmd compile

echo Running application with debug logging...
call mvnw.cmd spring-boot:run -Dspring-boot.run.jvmArguments="-Dlogging.level.com.example.QLySach_J2EE=DEBUG -Dlogging.level.org.springframework.web=DEBUG"

pause 