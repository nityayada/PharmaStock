/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nityayadav
 */
public class CustomerController {

    private static List<Customer> customers = new ArrayList<>();

    public CustomerController() {
        if (customers.isEmpty()) {
            customers.add(new Customer("C8583", "Hari Prasad Khadka", "9874672922", "hari@example.com"));
            // Add more sample customers if needed
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
}
