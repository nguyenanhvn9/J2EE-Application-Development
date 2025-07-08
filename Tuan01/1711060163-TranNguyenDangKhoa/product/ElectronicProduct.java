package product;

public class ElectronicProduct extends Product {
    private int power;
    private String voltage;

    public ElectronicProduct() {
    }

    public ElectronicProduct(String id, String name, float price, int quantityInStock, int power, String voltage) {
        super(id, name, price, quantityInStock);
        this.power = power;
        this.voltage = voltage;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    @Override
    public void display() {
        System.out.println("Electro");
        System.out.println("ID: " + getId());
        System.out.println("name: " + getName());
        System.out.println("price:" + getPrice() + "$");
        System.out.println("quantityInStock: " + getQuantityInStock());
        System.out.println("power: " + getPower());
        System.out.println("voltage: " + getVoltage());
    }

}