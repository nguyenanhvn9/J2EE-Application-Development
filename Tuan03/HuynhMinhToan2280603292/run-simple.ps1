Write-Host "========================================" -ForegroundColor Green
Write-Host "Running Spring Boot Application" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

Write-Host "Step 1: Checking Java..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java found: $($javaVersion[0])" -ForegroundColor Green
} catch {
    Write-Host "ERROR: Java not found!" -ForegroundColor Red
    Read-Host "Press Enter to continue"
    exit 1
}

Write-Host ""
Write-Host "Step 2: Trying to run with Maven wrapper..." -ForegroundColor Yellow
try {
    & .\mvnw.cmd spring-boot:run
} catch {
    Write-Host "Maven wrapper failed, trying alternative approach..." -ForegroundColor Yellow
    
    Write-Host ""
    Write-Host "Alternative: Please run the application using an IDE:" -ForegroundColor Cyan
    Write-Host "1. Open IntelliJ IDEA or Eclipse" -ForegroundColor White
    Write-Host "2. Import this project as a Maven project" -ForegroundColor White
    Write-Host "3. Run QLySachJ2EeApplication.java" -ForegroundColor White
    Write-Host "4. Access http://localhost:8080" -ForegroundColor White
}

Read-Host "Press Enter to continue" 