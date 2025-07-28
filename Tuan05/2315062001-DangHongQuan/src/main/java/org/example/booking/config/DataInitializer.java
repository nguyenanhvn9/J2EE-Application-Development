package org.example.booking.config;

import org.example.booking.Response.CategoryRepository;
import org.example.booking.Response.ProductRepository;
import org.example.booking.Response.RoleRepository;
import org.example.booking.Response.UserRepository;
import org.example.booking.model.Category;
import org.example.booking.model.Product;
import org.example.booking.model.Role;
import org.example.booking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create roles if they don't exist
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");

        // Create admin user if it doesn't exist
        createAdminUserIfNotExists();

        // Create sample categories
        createSampleCategories();

        // Create sample products
        createSampleProducts();
    }

    private void createRoleIfNotExists(String roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role(roleName);
            roleRepository.save(role);
        }
    }

    @Transactional
    protected void createAdminUserIfNotExists() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@techshop.com");
            admin.setFullName("System Administrator");
            admin.setIsActive(true);

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

            // Initialize roles collection if null
            if (admin.getRoles() == null) {
                admin.setRoles(new java.util.HashSet<>());
            }
            admin.getRoles().add(adminRole);

            userRepository.save(admin);
        }
    }

    private void createSampleCategories() {
        String[] categoryNames = {"Laptop", "Điện thoại", "Phụ kiện", "Tai nghe", "Màn hình"};

        for (String categoryName : categoryNames) {
            if (categoryRepository.findByNameIgnoreCase(categoryName) == null) {
                Category category = new Category(categoryName);
                categoryRepository.save(category);
            }
        }
    }

    private void createSampleProducts() {
        if (productRepository.count() == 0) {
            Category laptopCategory = categoryRepository.findByNameIgnoreCase("Laptop");
            Category phoneCategory = categoryRepository.findByNameIgnoreCase("Điện thoại");
            Category accessoryCategory = categoryRepository.findByNameIgnoreCase("Phụ kiện");

            // Sample laptops
            if (laptopCategory != null) {
                createProduct("MacBook Pro M2 13 inch 2024", "MacBook Pro mới nhất với chip M2 mạnh mẽ",
                        new BigDecimal("35000000"), 10, laptopCategory,
                        "https://example.com/macbook-pro-m2.jpg");

                createProduct("Dell XPS 13 Plus 2024", "Laptop Dell XPS cao cấp với thiết kế đẹp mắt",
                        new BigDecimal("28000000"), 15, laptopCategory,
                        "https://example.com/dell-xps-13.jpg");
            }

            // Sample phones
            if (phoneCategory != null) {
                createProduct("iPhone 15 Pro Max 256GB", "iPhone mới nhất với camera chuyên nghiệp",
                        new BigDecimal("32000000"), 20, phoneCategory,
                        "https://example.com/iphone-15-pro-max.jpg");

                createProduct("Samsung Galaxy S24 Ultra", "Flagship Android với S Pen tích hợp",
                        new BigDecimal("30000000"), 25, phoneCategory,
                        "https://example.com/samsung-s24-ultra.jpg");
            }

            // Sample accessories
            if (accessoryCategory != null) {
                createProduct("Chuột Logitech MX Master 3S", "Chuột không dây cao cấp cho dân văn phòng",
                        new BigDecimal("2500000"), 50, accessoryCategory,
                        "https://example.com/logitech-mx-master-3s.jpg");

                createProduct("Bàn phím Keychron K2 Wireless", "Bàn phím cơ không dây với switch hot-swap",
                        new BigDecimal("3200000"), 30, accessoryCategory,
                        "https://example.com/keychron-k2.jpg");
            }
        }
    }

    private void createProduct(String name, String description, BigDecimal price,
                               int stockQuantity, Category category, String imageUrl) {
        if (!productRepository.existsByNameIgnoreCase(name)) {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);
            product.setCategory(category);
            product.setImageUrl(imageUrl);
            product.setIsActive(true);

            productRepository.save(product);
        }
    }
}