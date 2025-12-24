/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author nityayadav
 */
public class ProductController {

    private static final List<Product> products = new ArrayList<>();

    // Load sample data only once
    public ProductController() {
        if (products.isEmpty()) {
            products.add(new Product("P0321", "Paracetamol Kalbe 500mg", 789, 2000.0));
            products.add(new Product("P0685", "Blackmores Vit C 1000mg", 540, 8000.0));
            products.add(new Product("P0998", "Nature Republic Aloe Vera", 48, 5000.0));
            products.add(new Product("P0389", "Minyak Telon My Baby 60ml", 20, 200.0));
            products.add(new Product("P0322", "Vitacid 0.025% 15gr", 0, 0.0));
            products.add(new Product("P0894", "Noroid Soothing 200ml", 120, 2100.0));
            // Add more if you want...
        }
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateProduct(String id, String name, int quantity, double price) {
        for (Product p : products) {
            if (p.getProductId().equals(id)) {
                p.setName(name);
                p.setQuantity(quantity);
                p.setPrice(price);
                break;
            }
        }
    }

    public void deleteProduct(String id) {
        products.removeIf(p -> p.getProductId().equals(id));
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

    public int getTotalProducts() {
        return products.size();
    }
}
