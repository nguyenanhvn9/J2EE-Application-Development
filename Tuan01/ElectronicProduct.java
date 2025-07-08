public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private int power;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, int power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    @Override
    public void display() {
        System.out.printf("Dien tu: %s | %s | Gia: %.2f | SL: %d | BH: %d thang | Cong suat: %dW\n",
                id, name, price, quantityInStock, warrantyMonths, power);
    }
}