# Script để chạy Maven wrapper với tên thư mục có dấu cách
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$mvnwPath = Join-Path $scriptDir "mvnw.cmd"

if (Test-Path $mvnwPath) {
    Write-Host "Running Maven wrapper from: $scriptDir"
    & $mvnwPath $args
} else {
    Write-Error "Maven wrapper not found at: $mvnwPath"
    exit 1
} 