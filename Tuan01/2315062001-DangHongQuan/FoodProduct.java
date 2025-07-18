class FoodProduct extends Product {
    private String productionDate;
    private String expirationDate;

    public FoodProduct(int id, String name, double price, int quantityInStock, String productionDate, String expirationDate) {
        super(id, name, price, quantityInStock);
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public void display() {
        System.out.println("Food Product: " + name + ", Price: " + price + ", Production Date: " + productionDate + ", Expiration Date: " + expirationDate);
    }
}
