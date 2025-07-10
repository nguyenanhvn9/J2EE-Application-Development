package product;

public abstract class Product {
    private String id;
    private String name;
    private float price;
    private int quantityInStock;

    public Product() {
    }

    public Product(String id, String name, float price, int quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

   public String getId() {
       return id;
   }

   public void setId(String id) {
       this.id = id;
   }

   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }

   public float getPrice() {
       return price;
   }

   public void setPrice(float price) {
       this.price = price;
   }

   public int getQuantityInStock() {
       return quantityInStock;
   }

   public void setQuantityInStock(int quantityInStock) {
       this.quantityInStock = quantityInStock;
   }

   public abstract void display();
}