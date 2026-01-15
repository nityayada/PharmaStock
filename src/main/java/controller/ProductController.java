package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductController {

    private static final ArrayList<Product> products = new ArrayList<>();

    // --- Manual Queue for Activity Log ---
    private static final int QUEUE_SIZE = 10;
    private static final String[] activityQueue = new String[QUEUE_SIZE];
    private static int front = -1;
    private static int rear = -1;

    // --- Manual Stack for Undo Delete ---
    private static final int STACK_MAX = 20;
    private static final Product[] undoStack = new Product[STACK_MAX];
    private static int top = -1;

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

            products.add(new Product("P1001", "Amoxicillin 500mg", 100, 35.0, LocalDate.of(2025, 8, 10),
                    "/images/amoxicillin.png"));
            products.add(new Product("P1002", "Ibuprofen 400mg", 25, 20.0, LocalDate.of(2024, 5, 15),
                    "/images/ibuprofen.png")); // Low
            // Stock
            products.add(new Product("P1003", "Cetirizine 10mg", 200, 15.0, LocalDate.of(2026, 11, 20),
                    "/images/cetirizine.png"));
            products.add(new Product("P1004", "Metformin 500mg", 80, 40.0, LocalDate.of(2025, 2, 28),
                    "/images/metformin.png")); // Near
            // Expiry
            // potentially
            products.add(new Product("P1005", "Amlodipine 5mg", 150, 30.0, LocalDate.of(2027, 3, 10),
                    "/images/amlodipine.png"));
            products.add(new Product("P1006", "Omeprazole 20mg", 40, 50.0, LocalDate.of(2025, 12, 5),
                    "/images/omeprazole.png")); // Low
            // Stock
            products.add(new Product("P1007", "Simvastatin 20mg", 90, 60.0, LocalDate.of(2026, 1, 30), "/images/simvastatin.png"));
            products.add(new Product("P1008", "Losartan 50mg", 120, 45.0, LocalDate.of(2025, 9, 30), "/images/losartan.png"));
            products.add(new Product("P1009", "Azithromycin 500mg", 60, 80.0, LocalDate.of(2025, 3, 1), "/images/azithromycin.png")); // Near
            // Expiry
            products.add(new Product("P1010", "Ciprofloxacin 500mg", 110, 35.0, LocalDate.of(2026, 8, 20), "/images/ciprofloxacin.png"));
            products.add(new Product("P1011", "Doxycycline 100mg", 30, 25.0, LocalDate.of(2024, 7, 10), "/images/doxycycline.png")); // Low
            // Stock
            products.add(new Product("P1012", "Gabapentin 300mg", 75, 150.0, LocalDate.of(2025, 11, 15), "/images/gabepentin.png"));
        }
    }

    // Helper to log activity (Manual Queue implementation)
    private void logActivity(String activity) {
        String logEntry = LocalTime.now().withNano(0) + ": " + activity;

        // Circular Queue logic or simple shift
        // If queue is full, we dequeue the oldest
        if (rear == QUEUE_SIZE - 1) {
            // dequeue oldest
            for (int i = 0; i < rear; i++) {
                activityQueue[i] = activityQueue[i + 1];
            }
            rear--;
        }

        if (front == -1) {
            front = 0;
        }

        rear++;
        activityQueue[rear] = logEntry;
    }

    public List<String> getRecentActivities() {
        List<String> activities = new ArrayList<>();
        if (front != -1) {
            for (int i = front; i <= rear; i++) {
                if (activityQueue[i] != null) {
                    activities.add(activityQueue[i]);
                }
            }
        }
        return activities;
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
        // Find product to delete - Manual Search
        Product toDelete = null;
        int deleteIndex = -1;

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(id)) {
                toDelete = products.get(i);
                deleteIndex = i;
                break;
            }
        }

        if (toDelete != null) {
            // Push to manual stack (add to end)
            if (top < STACK_MAX - 1) {
                top++;
                undoStack[top] = toDelete;
            }

            // Manual removal from ArrayList with shifting
            for (int i = deleteIndex; i < products.size() - 1; i++) {
                products.set(i, products.get(i + 1));
            }
            products.remove(products.size() - 1);

            logActivity("Deleted product: " + toDelete.getName());
        }
    }

    public boolean undoDelete() {
        if (top != -1) {
            // Pop from manual stack (remove from end)
            Product restored = undoStack[top];
            undoStack[top] = null;
            top--;

            products.add(restored);
            logActivity("Undid deletion of: " + restored.getName());
            return true;
        }
        return false;
    }

    // Linear Search Implementation
    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }

        List<Product> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        // Linear Search O(N)
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getName().toLowerCase().contains(lowerKeyword)
                    || p.getProductId().toLowerCase().contains(lowerKeyword)) {
                results.add(p);
            }
        }
        return results;
    }

    public List<Product> getLowStockProducts() {
        List<Product> results = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getQuantity() > 0 && p.getQuantity() <= 50) {
                results.add(p);
            }
        }
        return results;
    }

    public List<Product> getOutOfStockProducts() {
        List<Product> results = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getQuantity() == 0) {
                results.add(p);
            }
        }
        return results;
    }

    public List<Product> getExpiredProducts() {
        LocalDate today = LocalDate.now();
        List<Product> results = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getExpiryDate() != null && p.getExpiryDate().isBefore(today)) {
                results.add(p);
            }
        }
        return results;
    }

    public List<Product> getNearExpiryProducts(int days) {
        LocalDate today = LocalDate.now();
        LocalDate thresholdDate = today.plusDays(days);
        List<Product> results = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getExpiryDate() != null
                    && !p.getExpiryDate().isBefore(today)
                    && p.getExpiryDate().isBefore(thresholdDate)) {
                results.add(p);
            }
        }
        return results;
    }

    // === Sorting (Multiple Algorithms) ===
    public void sortProducts(String criteria, String algorithm) {
        switch (algorithm) {
            case "Quick Sort":
                quickSort(products, 0, products.size() - 1, criteria);
                break;
            case "Insertion Sort":
                insertionSort(products, criteria);
                break;
            case "Selection Sort":
                selectionSort(products, criteria);
                break;
            case "Merge Sort":
                mergeSort(products, 0, products.size() - 1, criteria);
                break;
            default:
                quickSort(products, 0, products.size() - 1, criteria);
        }
    }

    public void sortProducts(String criteria) {
        // Default to Quick Sort for backward compatibility
        sortProducts(criteria, "Quick Sort");
    }

    // Quick Sort
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
                case "Product ID":
                    condition = list.get(j).getProductId().compareToIgnoreCase(pivot.getProductId()) < 0;
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

    // Insertion Sort
    private void insertionSort(List<Product> list, String criteria) {
        int n = list.size();
        for (int i = 1; i < n; ++i) {
            Product key = list.get(i);
            int j = i - 1;

            while (j >= 0 && shouldSwap(list.get(j), key, criteria)) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
    }

    // --- Selection Sort ---
    private void selectionSort(List<Product> list, String criteria) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (shouldSwap(list.get(min_idx), list.get(j), criteria)) {
                    min_idx = j;
                }
            }
            // Swap
            Product temp = list.get(min_idx);
            list.set(min_idx, list.get(i));
            list.set(i, temp);
        }
    }

    // --- Merge Sort ---
    private void mergeSort(List<Product> list, int left, int right, String criteria) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(list, left, mid, criteria);
            mergeSort(list, mid + 1, right, criteria);

            merge(list, left, mid, right, criteria);
        }
    }

    private void merge(List<Product> list, int left, int mid, int right, String criteria) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Product> L = new ArrayList<>();
        List<Product> R = new ArrayList<>();

        for (int i = 0; i < n1; ++i) {
            L.add(list.get(left + i));
        }
        for (int j = 0; j < n2; ++j) {
            R.add(list.get(mid + 1 + j));
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (!shouldSwap(L.get(i), R.get(j), criteria)) { // If L <= R (depends on criteria logic "shouldSwap" is
                // effectively "isGreater")
                // Wait, shouldSwap logic: returns true if (left > key).
                // Merge sort needs (L <= R). So if !shouldSwap, then L <= R.
                list.set(k, L.get(i));
                i++;
            } else {
                list.set(k, R.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            list.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, R.get(j));
            j++;
            k++;
        }
    }

    // Helper to compare two products based on criteria
    // Returns true if p1 > p2 (for ascending order logic this means we swap/move)
    private boolean shouldSwap(Product p1, Product p2, String criteria) {
        switch (criteria) {
            case "Price":
                return p1.getPrice() > p2.getPrice();
            case "Quantity":
                return p1.getQuantity() > p2.getQuantity();
            case "Name":
                return p1.getName().compareToIgnoreCase(p2.getName()) > 0;
            case "Product ID":
                return p1.getProductId().compareToIgnoreCase(p2.getProductId()) > 0;
            default:
                return false;
        }
    }

    // Order Processing
    public Product getProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(id)) {
                return products.get(i);
            }
        }
        return null;
    }

    public void sellProduct(String id, int quantityToSell) {
        Product p = binarySearchProduct(id); // Use Binary Search for lookup
        if (p == null) {
            throw new IllegalArgumentException("Product not found: " + id);
        }
        if (p.getQuantity() < quantityToSell) {
            throw new IllegalArgumentException("Insufficient stock for product: " + p.getName());
        }

        p.setQuantity(p.getQuantity() - quantityToSell);
        logActivity("Sold " + quantityToSell + " of " + p.getName());
    }

    // Binary Search Algorithms O(log N) time complexity
    // Used for finding a product by explicit ID (requires sorting first)
    public Product binarySearchProduct(String productId) {
        // Ensure the list is sorted by Product ID before searching
        // We use our existing QuickSort for efficiency O(N log N)
        sortProducts("Product ID", "Quick Sort");

        int low = 0;
        int high = products.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Product midProduct = products.get(mid);
            int res = midProduct.getProductId().compareToIgnoreCase(productId);

            // Check if productId is present at mid
            if (res == 0) {
                return midProduct;
            }

            // If productId is greater, ignore left half
            if (res < 0) {
                low = mid + 1;
            } // If productId is smaller, ignore right half
            else {
                high = mid - 1;
            }
        }
        return null; // Not found
    }
}
