package com.techshop.service;

import com.techshop.model.Product;
import com.techshop.repository.ProductRepository;
import com.techshop.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        // Kiểm tra sản phẩm có liên kết với OrderItem không
        if (!orderItemRepository.findAll().stream().filter(oi -> oi.getProduct().getId().equals(id)).toList()
                .isEmpty()) {
            throw new RuntimeException("Không thể xóa sản phẩm vì đã có đơn hàng liên quan!");
        }
        productRepository.deleteById(id);
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Map<String, Object>> getTop5BestSellersThisMonth() {
        LocalDate firstDay = LocalDate.now().withDayOfMonth(1);
        Map<Long, Integer> productSales = new HashMap<>();
        orderItemRepository.findAll().forEach(item -> {
            if (item.getOrder().getCreatedAt().toLocalDate().isAfter(firstDay.minusDays(1))) {
                productSales.put(item.getProduct().getId(),
                        productSales.getOrDefault(item.getProduct().getId(), 0) + item.getQuantity());
            }
        });
        return productSales.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(5)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("productId", e.getKey());
                    map.put("quantity", e.getValue());
                    map.put("productName", productRepository.findById(e.getKey()).map(p -> p.getName()).orElse(""));
                    return map;
                })
                .toList();
    }
}