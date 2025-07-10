import java.util.ArrayList;
import java.util.List;

class Order {
    private List<Product> orderedProducts;

    public Order() {
        orderedProducts = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity) throws OutOfStockException {
        if (product.quantityInStock < quantity) {
            throw new OutOfStockException("Not enough stock for product: " + product.name);
        }
        product.quantityInStock -= quantity;
        orderedProducts.add(product);
    }

    public void displayOrder() {
        for (Product product : orderedProducts) {
            product.display();
        }
    }
}
