/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.User;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author nityayadav
 */
public class UserController {

    private static List<User> users = new ArrayList<>();

    public UserController() {
        if (users.isEmpty()) {
            users.add(new User("U001", "Silvia Sharma", "silvia@gmail.com", "9864892021", "cashier123", "Cashier"));
            users.add(new User("U002", "Admin User", "admin@gmail.com", "9801234567", "admin123", "Admin"));
        }
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public User login(String email, String password) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void updateUser(User updated) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(updated.getEmail())) {
                users.set(i, updated);
                break;
            }
        }
    }

    public void deleteUser(String email) {
        users.removeIf(u -> u.getEmail().equals(email));
    }

    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllUsers();
        }
        return users.stream()
                .filter(u -> u.getName().toLowerCase().contains(keyword.toLowerCase())
                || u.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<User> getUsersByRole(String role) {
        if (role.equals("All")) {
            return getAllUsers();
        }
        return users.stream()
                .filter(u -> u.getRole().equals(role))
                .collect(Collectors.toList());
    }
}
