package controller;

import java.time.LocalDate;
import model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {

    private static final List<Product> products = new ArrayList<>();

    // Load sample data only once
    public ProductController() {
        if (products.isEmpty()) {
            products.add(new Product(
                    "P0321",
                    "Paracetamol Kalbe 500mg",
                    789,
                    2000.0,
                    LocalDate.of(2026, 5, 20),
                    "images/paracetamol.png"
            ));

            products.add(new Product(
                    "P0685",
                    "Blackmores Vit C 1000mg",
                    540,
                    8000.0,
                    LocalDate.of(2027, 1, 15),
                    "images/vitc.png"
            ));

            products.add(new Product(
                    "P0998",
                    "Nature Republic Aloe Vera",
                    48,
                    5000.0,
                    LocalDate.of(2025, 12, 30),
                    "images/aloe.png"
            ));
        }
    }

    // ------------------ READ ------------------
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public int getTotalProducts() {
        return products.size();
    }

    // ------------------ CREATE ------------------
//    public void addProduct(Product product) {
//
//        // Validation
//        if (product == null) {
//            throw new IllegalArgumentException("Product cannot be null");
//        }
//
//        if (product.getExpiryDate() == null
//                || product.getExpiryDate().isBefore(LocalDate.now())) {
//            throw new IllegalArgumentException(
//                    "Expiry date must be a future date"
//            );
//        }
//
//        products.add(product);
//    }
    public void addProduct(Product product, LocalDate expiryDate, String imagePath) {
        if (expiryDate == null || expiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiry date must be a future date");
        }
        product.setExpiryDate(expiryDate);
        product.setImagePath(imagePath); // optional
        products.add(product);
    }

    // ------------------ UPDATE ------------------
//    public void updateProduct(
//            String id, Product name) {
//
//        for (Product p : products) {
//            if (p.getProductId().equals(id)) {
//
//                if (expiryDate == null
//                        || expiryDate.isBefore(LocalDate.now())) {
//                    throw new IllegalArgumentException(
//                            "Expiry date must be a future date"
//                    );
//                }
//
//                p.setName(name);
//                p.setQuantity(quantity);
//                p.setPrice(price);
//                p.setExpiryDate(expiryDate);
//                p.setImagePath(imagePath);
//                return;
//            }
//        }
//    }
    public void updateProduct(String id, String name, int quantity, double price, LocalDate expiryDate, String imagePath) {
        for (Product p : products) {
            if (p.getProductId().equals(id)) {
                if (expiryDate == null
                        || expiryDate.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException(
                            "Expiry date must be a future date"
                    );
                }
                p.setName(name);
                p.setQuantity(quantity);
                p.setPrice(price);
                p.setExpiryDate(expiryDate);
                p.setImagePath(imagePath);
                break;
            }
        }
    }

    // ------------------ DELETE ------------------
    public void deleteProduct(String id) {
        products.removeIf(p -> p.getProductId().equals(id));
    }

    // ------------------ SEARCH ------------------
    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }

        return products.stream()
                .filter(p
                        -> p.getName().toLowerCase().contains(keyword.toLowerCase())
                || p.getProductId().toLowerCase().contains(keyword.toLowerCase())
                )
                .collect(Collectors.toList());
    }

    // ------------------ FILTERS ------------------
    public List<Product> getLowStockProducts() {
        return products.stream()
                .filter(p -> p.getQuantity() > 0 && p.getQuantity() <= 50)
                .collect(Collectors.toList());
    }

    public List<Product> getOutOfStockProducts() {
        return products.stream()
                .filter(p -> p.getQuantity() == 0)
                .collect(Collectors.toList());
    }
}
