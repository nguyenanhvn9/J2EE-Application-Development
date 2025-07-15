@echo off
echo ========================================
echo Running Spring Boot with Java directly
echo ========================================
echo.

echo Step 1: Checking if Java is available...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH!
    pause
    exit /b 1
)

echo.
echo Step 2: Creating classpath...
set CLASSPATH=target\classes
if exist target\lib (
    for %%i in (target\lib\*.jar) do set CLASSPATH=!CLASSPATH!;%%i
)

echo.
echo Step 3: Running Spring Boot application...
echo Starting on http://localhost:8080
echo Press Ctrl+C to stop
echo.

java -cp "%CLASSPATH%" com.example.QLySach_J2EE.QLySachJ2EeApplication

pause 