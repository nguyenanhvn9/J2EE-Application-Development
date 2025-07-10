public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private int powerWatt;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, int powerWatt) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.powerWatt = powerWatt;
    }

    @Override
    public void display() {
        System.out.printf("Electronic - ID: %s, Name: %s, Price: %.2f, Stock: %d, Warranty: %d months, Power: %dW%n",
                id, name, price, quantityInStock, warrantyMonths, powerWatt);
    }
}