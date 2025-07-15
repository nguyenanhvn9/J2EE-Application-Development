# Simple PowerShell script to test endpoints
Write-Host "Testing Spring Boot Endpoints" -ForegroundColor Green
Write-Host "=============================" -ForegroundColor Green
Write-Host ""

# Wait for application to start
Write-Host "Waiting 10 seconds for application to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# Test endpoints
$endpoints = @(
    @{Path="/health"; Name="Health Check"},
    @{Path="/test-simple"; Name="Test Simple"},
    @{Path="/debug"; Name="Debug API"},
    @{Path="/test-page"; Name="Test Page"},
    @{Path="/books"; Name="Books Page"},
    @{Path="/"; Name="Home Page"}
)

foreach ($endpoint in $endpoints) {
    Write-Host "Testing $($endpoint.Name)..." -ForegroundColor Cyan
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080$($endpoint.Path)" -UseBasicParsing -TimeoutSec 5
        if ($response.StatusCode -eq 200) {
            Write-Host "✓ $($endpoint.Name) - OK (Status: $($response.StatusCode))" -ForegroundColor Green
        } else {
            Write-Host "✗ $($endpoint.Name) - Status: $($response.StatusCode)" -ForegroundColor Yellow
        }
    } catch {
        Write-Host "✗ $($endpoint.Name) - ERROR: $($_.Exception.Message)" -ForegroundColor Red
    }
    Write-Host ""
}

Write-Host "Testing completed!" -ForegroundColor Green
Write-Host "If you see any errors, check the application logs." -ForegroundColor Yellow 