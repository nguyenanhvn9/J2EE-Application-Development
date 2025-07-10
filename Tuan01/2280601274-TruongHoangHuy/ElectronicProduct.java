public class ElectronicProduct extends Product {
    private int warrantyPeriod; // thang
    private double power; // cong suat (Watt)

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyPeriod, double power) {
        super(id, name, price, quantityInStock);
        this.warrantyPeriod = warrantyPeriod;
        this.power = power;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public double getPower() {
        return power;
    }

    @Override
    public void display() {
        System.out.println("[Dien tu] ID: " + id + ", Ten: " + name + ", Gia: " + price + ", SL: " + quantityInStock +
                ", Bao hanh: " + warrantyPeriod + " thang, Cong suat: " + power + "W");
    }
}
