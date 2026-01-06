package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Product;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

public class ProductController {

    private static final ArrayList<Product> products = new ArrayList<>();

    private static final Queue<String> recentActivities = new LinkedList<>();

    private final Stack<Product> deletedProducts = new Stack<>();

    // Load sample data only once
    public ProductController() {
        if (products.isEmpty()) {
            logActivity("System Initialized with sample data");
            products.add(new Product(
                    "P0321",
                    "Paracetamol Kalbe 500mg",
                    789,
                    20.0,
                    LocalDate.of(2026, 5, 20),
                    "/images/paracetamol.png"));

            products.add(new Product(
                    "P0685",
                    "Blackmores Vit C 1000mg",
                    540,
                    80.0,
                    LocalDate.of(2027, 1, 15),
                    "/images/vitc.png"));

            products.add(new Product(
                    "P0998",
                    "Nature Republic Aloe Vera",
                    48,
                    50.0,
                    LocalDate.of(2025, 12, 30),
                    "/images/aloe.png"));

            //Additional Sample Data
            products.add(new Product("P1001", "Amoxicillin 500mg", 100, 35.0, LocalDate.of(2025, 8, 10), "/images/amoxicillin.png"));
            products.add(new Product("P1002", "Ibuprofen 400mg", 25, 20.0, LocalDate.of(2024, 5, 15), "/images/ibuprofen.png")); // Low
            // Stock
            products.add(new Product("P1003", "Cetirizine 10mg", 200, 15.0, LocalDate.of(2026, 11, 20), "/images/cetirizine.png"));
            products.add(new Product("P1004", "Metformin 500mg", 80, 40.0, LocalDate.of(2025, 2, 28), "/images/metformin.png")); // Near
            // Expiry
            // potentially
            products.add(new Product("P1005", "Amlodipine 5mg", 150, 30.0, LocalDate.of(2027, 3, 10), "/images/amlodipine.png"));
            products.add(new Product("P1006", "Omeprazole 20mg", 40, 50.0, LocalDate.of(2025, 12, 5), "/images/omeprazole.png")); // Low
            // Stock
            products.add(new Product("P1007", "Simvastatin 20mg", 90, 60.0, LocalDate.of(2026, 6, 15), null));
            products.add(new Product("P1008", "Losartan 50mg", 120, 45.0, LocalDate.of(2025, 9, 30), null));
            products.add(new Product("P1009", "Azithromycin 500mg", 60, 80.0, LocalDate.of(2025, 3, 1), null)); // Near
            // Expiry
            products.add(new Product("P1010", "Ciprofloxacin 500mg", 110, 35.0, LocalDate.of(2026, 8, 20), null));
            products.add(new Product("P1011", "Doxycycline 100mg", 30, 25.0, LocalDate.of(2024, 7, 10), null)); // Low
            // Stock
            products.add(new Product("P1012", "Gabapentin 300mg", 75, 150.0, LocalDate.of(2025, 11, 15), null));
        }
    }

    // Helper to log activity (Queue - FIFO)
    private void logActivity(String activity) {
        recentActivities.add(LocalTime.now().withNano(0) + ": " + activity);
        // Keep only last 10 activities
        if (recentActivities.size() > 10) {
            recentActivities.poll();
        }
    }

    public List<String> getRecentActivities() {
        return new ArrayList<>(recentActivities);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public int getTotalProducts() {
        return products.size();
    }

    public void addProduct(Product product, LocalDate expiryDate, String imagePath) {
        if (expiryDate == null || expiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiry date must be a future date");
        }
        product.setExpiryDate(expiryDate);
        product.setImagePath(imagePath); // optional
        products.add(product);
        logActivity("Added product: " + product.getName());
    }

    public void updateProduct(String id, String name, int quantity, double price, LocalDate expiryDate,
            String imagePath) {
        for (Product p : products) {
            if (p.getProductId().equals(id)) {
                if (expiryDate == null
                        || expiryDate.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException(
                            "Expiry date must be a future date");
                }
                p.setName(name);
                p.setQuantity(quantity);
                p.setPrice(price);
                p.setExpiryDate(expiryDate);
                p.setImagePath(imagePath);
                logActivity("Updated product: " + name);
                break;
            }
        }
    }

    public void deleteProduct(String id) {
        // Find product to delete
        Product toDelete = products.stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElse(null);

        if (toDelete != null) {
            deletedProducts.push(toDelete); // Push to stack
            products.remove(toDelete);

            logActivity("Deleted product: " + toDelete.getName());
        }
    }

    public boolean undoDelete() {
        if (!deletedProducts.isEmpty()) {
            Product restored = deletedProducts.pop(); // pop from stack
            products.add(restored);
            logActivity("Undid deletion of: " + restored.getName());
            return true;
        }
        return false;
    }

    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }

        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase())
                || p.getProductId().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

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

    public List<Product> getExpiredProducts() {
        LocalDate today = LocalDate.now();
        return products.stream()
                .filter(p -> p.getExpiryDate() != null && p.getExpiryDate().isBefore(today))
                .collect(Collectors.toList());
    }

    public List<Product> getNearExpiryProducts(int days) {
        LocalDate today = LocalDate.now();
        LocalDate thresholdDate = today.plusDays(days);
        return products.stream()
                .filter(p -> p.getExpiryDate() != null
                && !p.getExpiryDate().isBefore(today)
                && p.getExpiryDate().isBefore(thresholdDate))
                .collect(Collectors.toList());
    }

    // === Sorting (Quick Sort Implementation) ===
    public void sortProducts(String criteria) {
        quickSort(products, 0, products.size() - 1, criteria);
    }

    private void quickSort(List<Product> list, int low, int high, String criteria) {
        if (low < high) {
            int pi = partition(list, low, high, criteria);
            quickSort(list, low, pi - 1, criteria);
            quickSort(list, pi + 1, high, criteria);
        }
    }

    private int partition(List<Product> list, int low, int high, String criteria) {
        Product pivot = list.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            boolean condition = false;
            switch (criteria) {
                case "Price":
                    condition = list.get(j).getPrice() < pivot.getPrice();
                    break;
                case "Quantity":
                    condition = list.get(j).getQuantity() < pivot.getQuantity();
                    break;
                case "Name":
                    condition = list.get(j).getName().compareToIgnoreCase(pivot.getName()) < 0;
                    break;
            }
            if (condition) {
                i++;
                // Swap
                Product temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        // Swap pivot
        Product temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    // === Order Processing ===
    public Product getProduct(String id) {
        return products.stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public synchronized void sellProduct(String id, int quantityToSell) {
        Product p = getProduct(id);
        if (p == null) {
            throw new IllegalArgumentException("Product not found: " + id);
        }
        if (p.getQuantity() < quantityToSell) {
            throw new IllegalArgumentException("Insufficient stock for product: " + p.getName());
        }

        p.setQuantity(p.getQuantity() - quantityToSell);
        logActivity("Sold " + quantityToSell + " of " + p.getName());
    }
}
