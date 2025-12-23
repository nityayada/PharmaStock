/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nityayadav
 */
public class Transaction {

    private String transactionId;
    private String customerId;
    private LocalDate date;
    private LocalTime time;
    private double amount;
    private List<String> productIds; // List of product IDs in this transaction

    public Transaction(String transactionId, String customerId, LocalDate date, LocalTime time, double amount) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.productIds = new ArrayList<>();
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void addProduct(String productId) {
        productIds.add(productId);
    }
}
