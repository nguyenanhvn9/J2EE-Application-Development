package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value; // Import this

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${app.upload-dir}") // Inject the path from application.properties
    private String uploadDir;

    // Đường dẫn lưu file vào target/classes/static/images/
    private static final String UPLOAD_DIR = "D:/baitaptrenlopJ2EE/2280603547_HoAnhTuan/demo/target/classes/static/images/";

    public List<Product> getLatestProducts() {
        return productRepository.findTop6ByIsActiveTrueOrderByCreatedAtDesc();
    }

    public List<Product> getBestSellers() {
        return productRepository.findTop6ByOrderItemsQuantity();
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(String name, String description, double price, int stockQuantity, MultipartFile imageFile, Long categoryId) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(BigDecimal.valueOf(price));
        product.setStockQuantity(stockQuantity);
        product.setActive(true);

        // Xử lý upload file
        if (!imageFile.isEmpty()) {
            try {
                // Ensure the upload directory exists
                Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName); // Use resolve to correctly append filename to path

                System.out.println("Attempting to save file to: " + filePath.toAbsolutePath());

                // Check write permissions (good practice)
                if (!Files.isWritable(uploadPath)) {
                    System.err.println("Directory is not writable: " + uploadPath);
                    throw new IOException("Cannot write to directory: " + uploadPath);
                }

                Files.copy(imageFile.getInputStream(), filePath); // Use Files.copy for robustness
                System.out.println("File saved successfully: " + filePath);

                // Save relative path for serving
                product.setImageUrl("/uploads/" + fileName); // CHANGE THIS URL PREFIX!

            } catch (IOException e) {
                System.err.println("Failed to upload file: " + e.getMessage());
                e.printStackTrace();
                throw new IOException("Failed to upload image: " + e.getMessage(), e);
            }
        } else {
            System.out.println("No file uploaded, using default image");
            product.setImageUrl("/images/default.jpg"); // Still use default if no image uploaded
        }

        // Tìm category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        // Lưu product vào database
        Product savedProduct = productRepository.save(product);
        System.out.println("Product saved to database with imageUrl: " + savedProduct.getImageUrl());
        return savedProduct;
    }
    public Page<Product> getProducts(String keyword, Long categoryId, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (keyword != null && !keyword.trim().isEmpty() && categoryId != null) {
            return productRepository.findByKeywordAndCategoryId(keyword.trim(), categoryId, pageable);
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword.trim(), pageable);
        } else if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    // --- Phương thức THÊM sản phẩm mới ---
    // Cập nhật để nhận Product object thay vì từng tham số riêng lẻ
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            product.setImageUrl(imageUrl);
        }
        // Đặt thời gian tạo và cập nhật khi thêm mới
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    // --- Phương thức CẬP NHẬT sản phẩm hiện có ---
    public Product updateProduct(Product product, MultipartFile imageFile) throws IOException {
        // Tìm sản phẩm hiện có trong database
        Optional<Product> existingProductOpt = productRepository.findById(product.getId());
        if (existingProductOpt.isEmpty()) {
            throw new RuntimeException("Sản phẩm không tồn tại để cập nhật.");
        }

        Product existingProduct = existingProductOpt.get();

        // Cập nhật các trường từ đối tượng 'product' mới (đã được bind từ form)
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setActive(product.isActive()); // Cập nhật trạng thái active

        // Xử lý ảnh mới
        if (imageFile != null && !imageFile.isEmpty()) {
            // Nếu có ảnh mới, xóa ảnh cũ (nếu có) và lưu ảnh mới
            if (existingProduct.getImageUrl() != null && !existingProduct.getImageUrl().isEmpty()) {
                deleteImage(existingProduct.getImageUrl());
            }
            String newImageUrl = saveImage(imageFile);
            existingProduct.setImageUrl(newImageUrl);
        } else {
            // Nếu không upload ảnh mới, giữ nguyên ảnh cũ.
            // imageUrl trong 'product' object đã được bind từ hidden field trong form
            existingProduct.setImageUrl(product.getImageUrl());
        }

        // Cập nhật Category (đã được gán từ AdminController)
        existingProduct.setCategory(product.getCategory());

        // Cập nhật thời gian chỉnh sửa
        existingProduct.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(existingProduct);
    }

    // --- Phương thức XÓA sản phẩm ---
    public void deleteProduct(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            // Xóa ảnh vật lý liên quan nếu có
            if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
                try {
                    deleteImage(product.getImageUrl());
                } catch (IOException e) {
                    System.err.println("Lỗi khi xóa ảnh sản phẩm vật lý: " + e.getMessage());
                    // Có thể log lỗi nhưng không ngăn cản việc xóa sản phẩm khỏi DB
                }
            }
            productRepository.deleteById(productId);
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại để xóa.");
        }
    }
    private String saveImage(MultipartFile imageFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileExtension = "";
        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + fileExtension;
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(imageFile.getInputStream(), filePath);
        return "/images/products/" + fileName;
    }

    private void deleteImage(String imageUrl) throws IOException {
        if (imageUrl != null && imageUrl.startsWith("/images/products/")) {
            String fileName = imageUrl.substring("/images/products/".length());
            Path filePath = Paths.get(uploadDir, fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("Đã xóa ảnh cũ: " + filePath.toAbsolutePath());
            } else {
                System.out.println("Không tìm thấy ảnh cũ để xóa: " + filePath.toAbsolutePath());
            }
        }
    }
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}