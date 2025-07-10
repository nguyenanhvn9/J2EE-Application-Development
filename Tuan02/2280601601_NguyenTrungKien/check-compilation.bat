 @echo off
echo Checking Java compilation...
echo.

REM Kiểm tra Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 21 and add to PATH
    pause
    exit /b 1
)

echo Java is available
echo.

REM Thử compile với Maven nếu có
where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo Maven found, trying to compile...
    mvn clean compile
) else (
    echo Maven not found in PATH
    echo Please install Maven or use IDE to run the application
)

echo.
echo If compilation successful, you can run the application with:
echo mvn spring-boot:run
echo.
echo Or use IDE (IntelliJ IDEA, Eclipse, VS Code) to run the application
pause 