// Ngoại lệ khi sản phẩm hết hàng
public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
} 