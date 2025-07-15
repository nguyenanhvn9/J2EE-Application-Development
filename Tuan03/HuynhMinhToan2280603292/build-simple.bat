@echo off
echo Building Spring Boot application...
echo.

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Try to use Maven if available
mvn -version >nul 2>&1
if not errorlevel 1 (
    echo Using Maven to build...
    mvn clean package
    if not errorlevel 1 (
        echo Build successful! Starting application...
        java -jar target/QLySach_J2EE-0.0.1-SNAPSHOT.jar
    ) else (
        echo Maven build failed
        pause
        exit /b 1
    )
) else (
    echo Maven not found. Trying alternative approach...
    echo.
    echo Please install Maven or use IDE to build the project.
    echo.
    echo Alternative: Download Maven from https://maven.apache.org/download.cgi
    echo Then add Maven bin directory to your PATH environment variable.
    pause
) 