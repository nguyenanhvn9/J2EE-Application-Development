@echo off
echo ========================================
echo Running Spring Boot Application Directly
echo ========================================
echo.

echo Step 1: Checking Java version...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH!
    pause
    exit /b 1
)

echo.
echo Step 2: Creating target directory if it doesn't exist...
if not exist target mkdir target
if not exist target\classes mkdir target\classes

echo.
echo Step 3: Compiling Java files...
javac -cp ".;target\classes" -d target\classes src\main\java\com\example\QLySach_J2EE\*.java src\main\java\com\example\QLySach_J2EE\controller\*.java src\main\java\com\example\QLySach_J2EE\service\*.java src\main\java\com\example\QLySach_J2EE\model\*.java
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo.
echo Step 4: Copying resources...
if not exist target\classes\META-INF mkdir target\classes\META-INF
copy src\main\resources\*.* target\classes\ 2>nul
copy src\main\resources\templates\*.* target\classes\templates\ 2>nul

echo.
echo Step 5: Running Spring Boot application...
echo Starting on http://localhost:8080
echo Press Ctrl+C to stop
echo.

java -cp "target\classes;%CLASSPATH%" com.example.QLySach_J2EE.QLySachJ2EeApplication

pause 