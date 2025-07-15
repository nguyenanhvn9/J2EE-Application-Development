# PowerShell script to run Spring Boot application
Write-Host "========================================" -ForegroundColor Green
Write-Host "Starting Spring Boot Application" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Change to project directory
Set-Location "Tuan03/2280601601-NguyenTrungKien"

Write-Host "Current directory: $(Get-Location)" -ForegroundColor Yellow
Write-Host ""

# Clean and compile
Write-Host "Step 1: Cleaning and compiling..." -ForegroundColor Cyan
.\mvnw.cmd clean compile
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Build failed!" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "Build successful!" -ForegroundColor Green
Write-Host ""

# Run the application
Write-Host "Step 2: Starting Spring Boot application..." -ForegroundColor Cyan
Write-Host "Application will be available at: http://localhost:8080" -ForegroundColor Yellow
Write-Host ""
Write-Host "Test URLs:" -ForegroundColor Yellow
Write-Host "- Health Check: http://localhost:8080/health" -ForegroundColor White
Write-Host "- Test API: http://localhost:8080/test-simple" -ForegroundColor White
Write-Host "- Home Page: http://localhost:8080/" -ForegroundColor White
Write-Host "- Books Page: http://localhost:8080/books" -ForegroundColor White
Write-Host "- Test Page: http://localhost:8080/test-page" -ForegroundColor White
Write-Host ""
Write-Host "Press Ctrl+C to stop the application" -ForegroundColor Red
Write-Host ""

# Run the application
.\mvnw.cmd spring-boot:run 