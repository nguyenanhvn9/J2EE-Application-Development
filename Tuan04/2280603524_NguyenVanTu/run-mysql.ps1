# PowerShell script để chạy ứng dụng với MySQL
Write-Host "========================================" -ForegroundColor Green
Write-Host "   STUDENT MANAGEMENT SYSTEM" -ForegroundColor Green  
Write-Host "   Connecting to MySQL (Laragon)" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Kiểm tra Maven
Write-Host "Checking Maven..." -ForegroundColor Yellow
try {
    $mvnVersion = mvn -version 2>$null
    if ($mvnVersion) {
        Write-Host "✓ Maven found" -ForegroundColor Green
    } else {
        Write-Host "✗ Maven not found. Please install Maven first." -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "✗ Maven not found. Please install Maven first." -ForegroundColor Red
    exit 1
}

# Kiểm tra Laragon MySQL
Write-Host "Checking MySQL connection..." -ForegroundColor Yellow
$mysqlTest = Test-NetConnection -ComputerName localhost -Port 3306 -WarningAction SilentlyContinue
if ($mysqlTest.TcpTestSucceeded) {
    Write-Host "✓ MySQL is running on port 3306" -ForegroundColor Green
} else {
    Write-Host "✗ MySQL is not running. Please start Laragon and MySQL service." -ForegroundColor Red
    Write-Host "  1. Open Laragon" -ForegroundColor Cyan
    Write-Host "  2. Click 'Start All'" -ForegroundColor Cyan
    Write-Host "  3. Ensure MySQL service is green" -ForegroundColor Cyan
    exit 1
}

Write-Host ""
Write-Host "Starting Spring Boot application..." -ForegroundColor Yellow
Write-Host "Profile: MySQL" -ForegroundColor Cyan
Write-Host "Database: student_management" -ForegroundColor Cyan  
Write-Host "URL: http://localhost:8080" -ForegroundColor Cyan
Write-Host ""

# Chạy ứng dụng
try {
    mvn spring-boot:run -D"spring-boot.run.profiles=mysql"
} catch {
    Write-Host ""
    Write-Host "Error occurred while starting the application:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
}

Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
