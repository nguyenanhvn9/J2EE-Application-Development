# Test All Endpoints PowerShell Script
Write-Host "========================================" -ForegroundColor Green
Write-Host "Testing All Endpoints" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Wait for application to start
Write-Host "Waiting for application to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

Write-Host ""
Write-Host "Testing API Endpoints (should return JSON/text):" -ForegroundColor Cyan
Write-Host "------------------------------------------------"

# Test API endpoints
$endpoints = @(
    @{Path="/test-simple"; Name="Test Simple"},
    @{Path="/health"; Name="Health Check"},
    @{Path="/debug"; Name="Debug API"},
    @{Path="/test-books-simple"; Name="Test Books Simple"}
)

foreach ($endpoint in $endpoints) {
    Write-Host "Testing $($endpoint.Name) ($($endpoint.Path))..." -ForegroundColor White
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080$($endpoint.Path)" -UseBasicParsing -TimeoutSec 10
        Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
        Write-Host "Response: $($response.Content)" -ForegroundColor Gray
    } catch {
        Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    }
    Write-Host ""
}

Write-Host "Testing Web Pages (should return HTML):" -ForegroundColor Cyan
Write-Host "----------------------------------------"

# Test web pages
$webEndpoints = @(
    @{Path="/test-page"; Name="Test Page"; Expected="Test Page Works"},
    @{Path="/debug-page"; Name="Debug Page"; Expected="Debug Page"},
    @{Path="/books"; Name="Books Page"; Expected="Books"},
    @{Path="/"; Name="Home Page"; Expected="Todo"},
    @{Path="/home"; Name="Home"; Expected="Chào mừng"},
    @{Path="/about"; Name="About Page"; Expected="About"}
)

foreach ($endpoint in $webEndpoints) {
    Write-Host "Testing $($endpoint.Name) ($($endpoint.Path))..." -ForegroundColor White
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080$($endpoint.Path)" -UseBasicParsing -TimeoutSec 10
        Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
        
        if ($response.Content -match $endpoint.Expected) {
            Write-Host "✓ Page loaded successfully" -ForegroundColor Green
        } else {
            Write-Host "✗ Page content doesn't match expected" -ForegroundColor Yellow
        }
        
        # Check if it's a Whitelabel Error Page
        if ($response.Content -match "Whitelabel Error Page") {
            Write-Host "✗ WHITELABEL ERROR PAGE DETECTED!" -ForegroundColor Red
        }
        
    } catch {
        Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    }
    Write-Host ""
}

Write-Host "========================================" -ForegroundColor Green
Write-Host "Testing completed!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "If you see any '✗' messages, those endpoints need fixing." -ForegroundColor Yellow
Write-Host "Check the application logs for detailed error information." -ForegroundColor Yellow 