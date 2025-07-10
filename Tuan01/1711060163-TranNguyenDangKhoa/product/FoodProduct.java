package product;

public class FoodProduct extends Product {
    private String expiryDate;
    private String storage;

    public FoodProduct() {
    }

    public FoodProduct(String id, String name, float price, int quantityInStock, String expiryDate, String storage) {
        super(id, name, price, quantityInStock);
        this.expiryDate = expiryDate;
        this.storage = storage;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    @Override
    public void display() {
        System.out.println("Food Product");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice() + "$");
        System.out.println("Quantity in Stock: " + getQuantityInStock());
        System.out.println("Expiry Date: " + getExpiryDate());
        System.out.println("Storage Conditions: " + getStorage());
    }

}