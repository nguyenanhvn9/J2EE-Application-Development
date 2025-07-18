public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private double power;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, double power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    @Override
    public void display() {
        System.out.println("Electronic Product - ID: " + id + ", Name: " + name + ", Price: " + price + 
                          ", Quantity: " + quantityInStock + ", Warranty: " + warrantyMonths + " months, Power: " + power + "W");
    }
}