package order;

// Custom exception cho trường hợp hết hàng hoặc không tìm thấy sản phẩm
public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}