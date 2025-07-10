# 2280602159-DoanHuuNhan - Inventory Management System

## Structure
- `model/`: Product classes (abstract and subclasses)
- `manager/`: InventoryManager (Singleton)
- `order/`: Order class
- `exception/`: Custom exceptions
- `cli/`: Main CLI application
- `util/`: JUnit tests

## Features
- Object-Oriented Design with polymorphism
- Inventory management (add, remove, update, search, display)
- Order processing with stock check and exception
- File I/O for inventory persistence
- Java Stream API for searching/filtering
- JUnit tests for business logic

## How to Run
1. Compile all Java files:
   ```
   javac model/*.java manager/*.java order/*.java exception/*.java cli/*.java
   ```
2. Run the CLI:
   ```
   java cli.Main
   ```
3. To run tests (JUnit 5 required):
   - Add JUnit 5 to your classpath
   - Run `util/TestInventoryManager.java` with your IDE or build tool
