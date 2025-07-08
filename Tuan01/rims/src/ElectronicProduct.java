public class ElectronicProduct extends Product {
    private int warrantyMonths;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public void display() {
        System.out.println("Electronic: " + name + ", ID: " + id + ", Price: " + price + ", Qty: " + quantityInStock + ", Warranty: " + warrantyMonths + " months");
    }
}
