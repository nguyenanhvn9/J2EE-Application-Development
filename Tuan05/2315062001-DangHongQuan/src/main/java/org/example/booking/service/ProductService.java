package org.example.booking.service;

import org.example.booking.Response.ProductRepository;
import org.example.booking.model.Category;
import org.example.booking.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Customer facing methods
    public Page<Product> getAllActiveProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByIsActiveTrueOrderByCreatedAtDesc(pageable);
    }

    public Page<Product> getProductsByCategory(Category category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategoryAndIsActiveTrueOrderByCreatedAtDesc(category, pageable);
    }

    public Page<Product> searchProducts(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContainingIgnoreCaseAndIsActiveTrueOrderByCreatedAtDesc(searchTerm, pageable);
    }

    public Page<Product> searchProductsByCategory(Category category, String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategoryAndNameContainingIgnoreCaseAndIsActiveTrueOrderByCreatedAtDesc(
                category, searchTerm, pageable);
    }

    public List<Product> getLatestProducts() {
        return productRepository.findTop8ByIsActiveTrueOrderByCreatedAtDesc();
    }

    public List<Product> getBestSellingProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRepository.findBestSellingProducts(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Admin methods
    public Page<Product> getAllProductsForAdmin(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProductsForAdmin(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
    }

    public Page<Product> getProductsByCategoryForAdmin(Category category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategory(category, pageable);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public boolean isProductNameExists(String name, Long excludeId) {
        if (excludeId != null) {
            return productRepository.existsByNameIgnoreCaseAndIdNot(name, excludeId);
        }
        return productRepository.existsByNameIgnoreCase(name);
    }

    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findByStockQuantityLessThanAndIsActiveTrue(threshold);
    }

    // Business logic methods
    public void reduceStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.reduceStock(quantity);
        productRepository.save(product);
    }

    public void increaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.increaseStock(quantity);
        productRepository.save(product);
    }

    public boolean isProductAvailable(Long productId, int requestedQuantity) {
        Product product = productRepository.findById(productId)
                .orElse(null);
        return product != null && product.isAvailable() &&
                product.getStockQuantity() >= requestedQuantity;
    }
}