package Tuan01;

public class FoodProduct extends Product {
    private String manufactureDate;
    private String expiryDate;
    public FoodProduct(String id, String name, double price, int quantityInStock, String manufactureDate, String expiryDate) {
        super(id, name, price, quantityInStock);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public void displayInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice());
        System.out.println("Quantity in Stock: " + getQuantityInStock());
        System.out.println("Expiration Date: " + manufactureDate);
        System.out.println("Ingredients: " + expiryDate);
    }
    
    public String getManufactureDate() {
        return manufactureDate;
    }
    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
