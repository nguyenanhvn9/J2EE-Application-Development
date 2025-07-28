package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.example.demo.entity.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private OrderStatus status;

    @Column(nullable = false, length = 100)
    private String customerName;

    // Trong Order.java (entity)
    @Column(name = "shipping_address", nullable = true) // Đảm bảo rằng nó CÓ THỂ NULL
    private String shippingAddress;

    @Column(nullable = false, length = 20)
    private String customerPhone;

    @Column(name = "total_amount", nullable = false) // Đảm bảo trường này là NOT NULL
    private BigDecimal totalAmount;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime createdAt; // Should be set automatically on creation

    private LocalDateTime updatedAt;
    private String updatedBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    // Constructor mặc định: Khởi tạo createdAt
    public Order() {
        this.createdAt = LocalDateTime.now();
    }

    public enum DeliveryMethod {
        DELIVERY("Giao hàng tận nơi"),
        PICKUP("Nhận tại cửa hàng");

        private final String description;

        DeliveryMethod(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DeliveryMethod deliveryMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_store_id")
    private Store pickupStore;

    // --- NEW FIELD: Shipping Fee ---
    @Column(name = "shipping_fee", precision = 10, scale = 2)
    private BigDecimal shippingFee = BigDecimal.ZERO; // Phí vận chuyển
    // --- END NEW FIELD ---

    // --- NEW ENUM: Shipping Method (if not already defined elsewhere) ---
    public enum ShippingMethod {
        STANDARD("Giao hàng tiêu chuẩn"),
        EXPRESS("Giao hàng nhanh");

        private final String description;

        ShippingMethod(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method", length = 50)
    private ShippingMethod shippingMethod; // Phương thức vận chuyển
    // --- END NEW ENUM ---

    // Ensure orderDate is a single field declaration
    @Column(nullable = false)
    private LocalDateTime orderDate;


    // --- Getters and Setters ---
// Getters and Setters
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    // --- CORRECTED/SINGLE getTotalAmount() METHOD ---
    // This method calculates the total dynamically.
    // Ensure you only have ONE definition for getTotalAmount().

    // --- REMOVE setTotalAmount() if totalAmount is calculated dynamically ---
    // public void setTotalAmount(BigDecimal totalAmount) {
    //     this.totalAmount = totalAmount;
    // }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Store getPickupStore() {
        return pickupStore;
    }

    public void setPickupStore(Store pickupStore) {
        this.pickupStore = pickupStore;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    // --- NEW GETTER/SETTER FOR SHIPPING FEE ---
    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }
    // --- END NEW GETTER/SETTER ---

    // --- GETTER/SETTER FOR SHIPPING METHOD ---
    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    // --- END GETTER/SETTER ---

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}