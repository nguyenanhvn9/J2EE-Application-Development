Write-Host "========================================" -ForegroundColor Green
Write-Host "Fixing and Running Spring Boot Project" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

Write-Host "Step 1: Cleaning previous builds..." -ForegroundColor Yellow
if (Test-Path "target") {
    Remove-Item -Recurse -Force "target"
    Write-Host "Cleaned target directory." -ForegroundColor Green
}

Write-Host ""
Write-Host "Step 2: Building project with Maven..." -ForegroundColor Yellow
& .\mvnw.cmd clean compile
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Build failed! Please check the errors above." -ForegroundColor Red
    Read-Host "Press Enter to continue"
    exit 1
}
Write-Host "Build successful!" -ForegroundColor Green

Write-Host ""
Write-Host "Step 3: Running the application..." -ForegroundColor Yellow
Write-Host "Starting Spring Boot application on http://localhost:8080" -ForegroundColor Cyan
Write-Host "Press Ctrl+C to stop the application" -ForegroundColor Cyan
Write-Host ""
& .\mvnw.cmd spring-boot:run

Read-Host "Press Enter to continue" 