class ClothingProduct extends Product {
    private String size;
    private String material;

    public ClothingProduct(int id, String name, double price, int quantityInStock, String size, String material) {
        super(id, name, price, quantityInStock);
        this.size = size;
        this.material = material;
    }

    @Override
    public void display() {
        System.out.println("Clothing Product: " + name + ", Price: " + price + ", Size: " + size + ", Material: " + material);
    }
}
