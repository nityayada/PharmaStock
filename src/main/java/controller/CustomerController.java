/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author nityayadav
 */
public class CustomerController {

    private static List<Customer> customers = new ArrayList<>();

    public CustomerController() {
        if (customers.isEmpty()) {
            customers.add(new Customer("C8583", "Hari Prasad Khadka", "9874672922", "hari@example.com"));

        }
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void deleteCustomer(String id) {
        customers.removeIf(c -> c.getCustomerId().equals(id));
    }

    public int getTotalCustomers() {
        return customers.size();
    }

    public List<Customer> searchCustomers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCustomers();
        }
        return customers.stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase())
                || c.getPhoneNumber().contains(keyword)
                || c.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
