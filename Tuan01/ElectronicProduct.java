package Tuan01;

public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private double power;

    public ElectronicProduct(String id, String name, double price, int quantityInStock,
                             int warrantyMonths, double power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    @Override
    public void display() {
        System.out.printf("Electronic: ID=%s, Name=%s, Price=%.2f, Qty=%d, Warranty=%d months, Power=%.2fW\n",
                          id, name, price, quantityInStock, warrantyMonths, power);
    }
}

