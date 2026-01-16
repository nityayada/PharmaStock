/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Represents a Customer in the PharmaStock system.
 * Stores personal and contact information for pharmacy clients.
 * 
 * @author nityayadav
 */
public class Customer {

    private final String customerId;
    private String name;
    private String phoneNumber;
    private String email; // Optional for the customer

    /**
     * Constructs a new Customer with the specified details.
     * 
     * @param customerId  The unique identifier for the customer.
     * @param name        The full name of the customer.
     * @param phoneNumber The contact phone number.
     * @param email       The contact email address (optional).
     */
    public Customer(String customerId, String name, String phoneNumber, String email) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
