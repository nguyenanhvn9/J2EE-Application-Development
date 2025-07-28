package com.example.demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.demo.entity.OrderItem;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @NotBlank(message = "Tên sản phẩm không được để trống.")
    @Size(min = 10, max = 255, message = "Tên sản phẩm phải từ 10 đến 255 ký tự.")
    @Pattern(regexp = "^[a-zA-Z0-9\\sÀÁẠẢÃàáạảãĂẮẶẲẴăắặẳẵÂẤẬẨẪâấậẩẫÈÉẸẺẼèéẹẻẽÊẾỆỂỄêếệểễÌÍỊỈĨìíịỉĩÒÓỌỎÕòóọỏõÔỐỘỔỖôốộổỗƠỚỢỞỠơớợởỡÙÚỤỦŨùúụủũƯỨỰỬỮưứựửữỲÝỴỶỸỳýỵỷỹĐđ\\[\\]\\(\\)\\-\\/\\_\\.]*$",
            message = "Tên sản phẩm không được chứa ký tự đặc biệt.") // Cho phép chữ cái, số, khoảng trắng, các dấu [], (), -, /, _, . và tiếng Việt
    @Column(nullable = false)
    private String name;

    @Size(max = 2000, message = "Mô tả không được vượt quá 2000 ký tự.")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Giá bán không được để trống.")
    @DecimalMin(value = "0.01", message = "Giá bán phải là một số dương lớn hơn 0.")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "Số lượng tồn kho không được để trống.")
    @Min(value = 0, message = "Số lượng tồn kho không được âm.")
    @Max(value = 9999, message = "Số lượng tồn kho không được vượt quá 9999.")
    @Column(nullable = false)
    private int stockQuantity;

    private String imageUrl;

    @Column(nullable = false)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Vui lòng chọn một danh mục.")
    private Category category;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    // --- THÊM PHẦN NÀY ĐỂ LIÊN KẾT VỚI REVIEWS ---
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>();
    // --- KẾT THÚC PHẦN THÊM ---

    public Product() {
    }

    // Add this to calculate average rating
    @Transient // Không lưu vào database
    public Double getAverageRating() {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal  getPrice() { return price; }
    public void setPrice(BigDecimal  price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Set<Review> getReviews() { return reviews; }
    public void setReviews(Set<Review> reviews) { this.reviews = reviews; }

    // Utility methods to maintain bidirectional relationship
    public void addReview(Review review) {
        reviews.add(review);
        review.setProduct(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setProduct(null);
    }
}