package product;

public class ClothingProduct extends Product {
    private String size;
    private String material;

    public ClothingProduct() {
    }

    public ClothingProduct(String id, String name, float price, int quantityInStock, String size, String material) {
        super(id, name, price, quantityInStock);
        this.size = size;
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void display() {
        System.out.println("Clothing Product");
        System.out.println("ID: " +

                getId());
        System.out.println("Name: " +

                getName());
        System.out.println("Price: " +

                getPrice() + "$");
        System.out.println("Quantity in Stock: " +

                getQuantityInStock());
        System.out.println("Size: " +

                getSize());
        System.out.println("Material: " +

                getMaterial());
    }
}