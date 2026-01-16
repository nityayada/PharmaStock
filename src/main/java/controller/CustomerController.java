/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing Customer-related operations.
 * Handles the storage, retrieval, and searching of customer records.
 * 
 * @author nityayadav
 */
public class CustomerController {

    private static List<Customer> customers = new ArrayList<>();

    /**
     * Constructs a new CustomerController and initializes it with sample data if
     * the list is empty.
     */
    public CustomerController() {
        if (customers.isEmpty()) {
            customers.add(new Customer("C8583", "Hari Prasad Khadka", "9874672922", "hari@example.com"));
            customers.add(new Customer("C8584", "Sita Sharma", "9841234567", "sita@example.com"));
            customers.add(new Customer("C8585", "Ram Bahadur Tha", "9801987654", "ram.b@example.com"));
            customers.add(new Customer("C8586", "Gita Newa", "9812345678", "gita.n@example.com"));
            customers.add(new Customer("C8587", "Nabin Rai", "9867890123", "nabin@example.com"));

        }
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void deleteCustomer(String id) {
        int index = -1;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            // Manual Shift
            for (int i = index; i < customers.size() - 1; i++) {
                customers.set(i, customers.get(i + 1));
            }
            customers.remove(customers.size() - 1);
        }
    }

    public int getTotalCustomers() {
        return customers.size();
    }

    public List<Customer> searchCustomers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCustomers();
        }
        List<Customer> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            if (c.getName().toLowerCase().contains(lowerKeyword)
                    || c.getPhoneNumber().contains(keyword)
                    || c.getEmail().toLowerCase().contains(lowerKeyword)) {
                results.add(c);
            }
        }
        return results;
    }
}
