package model;

import java.time.LocalDate;

public class FoodProduct extends Product {
  private LocalDate manufactureDate;
  private LocalDate expiryDate;

  public FoodProduct(String id, String name, double price, int quantity, LocalDate manufactureDate,
      LocalDate expiryDate) {
    super(id, name, price, quantity);
    this.manufactureDate = manufactureDate;
    this.expiryDate = expiryDate;
  }

  @Override
  public void display() {
    System.out.printf("[Food] ID: %s | Name: %s | Price: %.2f | Qty: %d | Mfg: %s | Exp: %s\n",
        id, name, price, quantityInStock, manufactureDate, expiryDate);
  }
}
