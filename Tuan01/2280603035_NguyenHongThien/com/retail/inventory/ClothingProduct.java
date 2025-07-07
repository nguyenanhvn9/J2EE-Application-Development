package com.retail.inventory;

/**
 * Represents a clothing product.
 */
public class ClothingProduct extends Product {
    private String size;
    private String material;

    public ClothingProduct(String id, String name, double price, int quantityInStock,
                           String size, String material) {
        super(id, name, price, quantityInStock);
        setSize(size);
        setMaterial(material);
    }

    public String getSize() { return size; }
    public void setSize(String size) {
        if (size == null || size.trim().isEmpty())
            throw new IllegalArgumentException("Size cannot be empty.");
        this.size = size.trim();
    }

    public String getMaterial() { return material; }
    public void setMaterial(String material) {
        if (material == null || material.trim().isEmpty())
            throw new IllegalArgumentException("Material cannot be empty.");
        this.material = material.trim();
    }

    @Override
    public void display() {
        System.out.printf("ClothingProduct [ID=%s, Name=%s, Price=%.2f, Stock=%d, Size=%s, Material=%s]%n",
                getId(), getName(), getPrice(), getQuantityInStock(), size, material);
    }
}
