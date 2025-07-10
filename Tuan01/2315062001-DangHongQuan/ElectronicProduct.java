class ElectronicProduct extends Product {
    private int warrantyMonths;
    private double power;

    public ElectronicProduct(int id, String name, double price, int quantityInStock, int warrantyMonths, double power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    @Override
    public void display() {
        System.out.println("Electronic Product: " + name + ", Price: " + price + ", Warranty: " + warrantyMonths + " months, Power: " + power + "W");
    }
}
