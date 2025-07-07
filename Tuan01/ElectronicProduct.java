public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private int power;

    public ElectronicProduct(String id, String name, double price, int quantity, int warrantyMonths, int power) {
        super(id, name, price, quantity);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    @Override
    public void display() {
        System.out.println("[Electronic] " + name + " - Price: " + price + ", Stock: " + quantityInStock + ", Warranty: " + warrantyMonths + " months, Power: " + power + "W");
    }
}