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
 * Represents a sales Transaction in the system.
 * This class is immutable for reliability of financial data.
 * 
 * @author nityayadav
 */
public class Transaction {

    // using the final keyword so the details can't be changed which makes the
    // financal data relaible
    private final String transactionId;
    private final String customerId;
    private final LocalDate date;
    private final LocalTime time;
    private final double amount;
    private final List<String> productIds;
    // List of product IDs in this transaction using list here
    // so it will be easy to change in the other list like linkedlist

    /**
     * Constructs a new Transaction with the specified details.
     * 
     * @param transactionId Unique identifier for the transaction.
     * @param customerId    ID of the customer involved in the transaction.
     * @param date          Date of the transaction.
     * @param time          Time of the transaction.
     * @param amount        Total price amount of the sale.
     */
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
