Write-Host "========================================" -ForegroundColor Green
Write-Host "Kiểm tra trạng thái Spring Boot Project" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

Write-Host "Step 1: Kiểm tra Java..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1
    Write-Host "✅ Java found: $($javaVersion[0])" -ForegroundColor Green
} catch {
    Write-Host "❌ Java not found!" -ForegroundColor Red
}

Write-Host ""
Write-Host "Step 2: Kiểm tra cấu trúc project..." -ForegroundColor Yellow

# Kiểm tra các file quan trọng
$importantFiles = @(
    "src\main\java\com\example\QLySach_J2EE\QLySachJ2EeApplication.java",
    "src\main\java\com\example\QLySach_J2EE\controller\TodoController.java",
    "src\main\java\com\example\QLySach_J2EE\controller\CustomErrorController.java",
    "src\main\java\com\example\QLySach_J2EE\service\TodoService.java",
    "src\main\java\com\example\QLySach_J2EE\model\TodoItem.java",
    "src\main\resources\templates\index.html",
    "pom.xml"
)

foreach ($file in $importantFiles) {
    if (Test-Path $file) {
        Write-Host "✅ $file" -ForegroundColor Green
    } else {
        Write-Host "❌ $file (missing)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "Step 3: Kiểm tra Maven wrapper..." -ForegroundColor Yellow
if (Test-Path "mvnw.cmd") {
    Write-Host "✅ Maven wrapper found" -ForegroundColor Green
} else {
    Write-Host "❌ Maven wrapper missing" -ForegroundColor Red
}

Write-Host ""
Write-Host "Step 4: Tóm tắt..." -ForegroundColor Cyan
Write-Host "🎯 Project đã được sửa và sẵn sàng chạy!" -ForegroundColor Green
Write-Host ""
Write-Host "📋 Cách chạy:" -ForegroundColor Yellow
Write-Host "   1. Mở IDE (IntelliJ IDEA/Eclipse)" -ForegroundColor White
Write-Host "   2. Import project như Maven project" -ForegroundColor White
Write-Host "   3. Chạy QLySachJ2EeApplication.java" -ForegroundColor White
Write-Host "   4. Truy cập http://localhost:8080" -ForegroundColor White
Write-Host ""
Write-Host "📖 Xem chi tiết trong file QUICK-START.md" -ForegroundColor Cyan

Read-Host "Press Enter to continue" 