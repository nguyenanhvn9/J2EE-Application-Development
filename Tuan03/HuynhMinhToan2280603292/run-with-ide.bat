@echo off
echo ========================================
echo SPRING BOOT APPLICATION - Tuan03
echo ========================================
echo.
echo Since Maven is not available, please use one of these methods:
echo.
echo METHOD 1: Use IntelliJ IDEA
echo 1. Open IntelliJ IDEA
echo 2. Open project: Tuan03/2280601601-NguyenTrungKien
echo 3. Wait for Maven to download dependencies
echo 4. Right-click on QLySachJ2EeApplication.java
echo 5. Select "Run QLySachJ2EeApplication"
echo.
echo METHOD 2: Install Maven
echo 1. Download Maven from: https://maven.apache.org/download.cgi
echo 2. Extract to C:\Program Files\Apache\maven
echo 3. Add C:\Program Files\Apache\maven\bin to PATH
echo 4. Restart PowerShell/CMD
echo 5. Run: mvn clean package
echo 6. Run: java -jar target/QLySach_J2EE-0.0.1-SNAPSHOT.jar
echo.
echo METHOD 3: Use Eclipse
echo 1. Open Eclipse
echo 2. Import existing Maven project
echo 3. Right-click project -> Run As -> Spring Boot App
echo.
echo ========================================
echo After running, access: http://localhost:8080
echo ========================================
pause 