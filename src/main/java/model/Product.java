package model;

import java.time.LocalDate;

/**
 *
 * @author nityayadav
 */
public class Product {

    private final String productId;
    private String name;
    private int quantity;
    private double price;
    private String status; // "Available", "Low Stock", "Empty"
    private LocalDate expiryDate; // compulsory
    private String imagePath; // product image path (optiona)

    public Product(
            String productId,
            String name,
            int quantity,
            double price,
            LocalDate expiryDate,
            String imagePath) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
        this.imagePath = imagePath;
        updateStatus();
    }

    private void updateStatus() {
        if (quantity > 50) {
            this.status = "Available";
        } else if (quantity > 0) {
            this.status = "Low Stock";
        } else {
            this.status = "Out of Stock";
        }
    }

    // Getters & Setters
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateStatus();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
