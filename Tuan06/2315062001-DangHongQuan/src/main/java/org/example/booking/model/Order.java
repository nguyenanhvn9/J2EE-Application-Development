package org.example.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "order_date", nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @NotBlank(message = "Order status is required")
    @Size(max = 50, message = "Status cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String status = "PENDING";

    @NotNull(message = "Total amount is required")
    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @NotBlank(message = "Customer name is required")
    @Size(max = 100, message = "Customer name cannot exceed 100 characters")
    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @NotBlank(message = "Shipping address is required")
    @Size(max = 255, message = "Shipping address cannot exceed 255 characters")
    @Column(name = "shipping_address", nullable = false, length = 255)
    private String shippingAddress;

    @NotBlank(message = "Customer phone is required")
    @Size(max = 20, message = "Customer phone cannot exceed 20 characters")
    @Column(name = "customer_phone", nullable = false, length = 20)
    private String customerPhone;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Size(max = 50, message = "Updated by cannot exceed 50 characters")
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems = new HashSet<>();

    // Constructors
    public Order() {}

    public Order(User user, BigDecimal totalAmount, String customerName,
                 String shippingAddress, String customerPhone) {
        this.user = user;
        this.totalAmount = totalAmount;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.customerPhone = customerPhone;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public Set<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(Set<OrderItem> orderItems) { this.orderItems = orderItems; }

    // Business methods
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    public boolean canBeCancelled() {
        return "PENDING".equals(status) || "PROCESSING".equals(status);
    }

    public boolean canBeUpdated() {
        return "PENDING".equals(status);
    }

    public void updateStatus(String newStatus, String updatedBy) {
        if (isValidStatusTransition(this.status, newStatus)) {
            this.status = newStatus;
            this.updatedBy = updatedBy;
        } else {
            throw new IllegalArgumentException("Invalid status transition from " + this.status + " to " + newStatus);
        }
    }

    private boolean isValidStatusTransition(String currentStatus, String newStatus) {
        return switch (currentStatus) {
            case "PENDING" -> "PROCESSING".equals(newStatus) || "CANCELLED".equals(newStatus);
            case "PROCESSING" -> "SHIPPED".equals(newStatus) || "CANCELLED".equals(newStatus);
            case "SHIPPED" -> "DELIVERED".equals(newStatus);
            default -> false;
        };
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", status='" + status + "', totalAmount=" + totalAmount +
                ", customerName='" + customerName + "'}";
    }
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @Column(name = "discount_amount", precision = 12, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;
    public void applyVoucher(Voucher voucher) {
        if (voucher != null && voucher.isValid()) {
            this.voucher = voucher;
            this.discountAmount = voucher.getDiscountAmount();
            this.totalAmount = this.totalAmount.subtract(discountAmount);
            voucher.incrementUsage();
        }
    }
    @ManyToOne
    private Address addressShip;
    // Order.java
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    public List<OrderItem> getItems() {
        return items;
    }

}