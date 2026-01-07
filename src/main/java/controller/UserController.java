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
            users.add(
                    new User("U001", "Silvia Sharma", "silvia@gmail.com", "9864892021", "cashier123", "Cashier", null));
            users.add(new User("U002", "Admin User", "admin@gmail.com", "9801234567", "admin123", "Admin", null));
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

        // Manual Linear Search O(N)
        List<User> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getName().toLowerCase().contains(lowerKeyword)
                    || u.getEmail().toLowerCase().contains(lowerKeyword)) {
                result.add(u);
            }
        }

        sortUsersByName(result); // Sort search results too
        return result;
    }

    public List<User> getUsersByRole(String role) {
        List<User> result;
        if (role.equals("All")) {
            result = getAllUsers();
        } else {
            result = users.stream()
                    .filter(u -> u.getRole().equals(role))
                    .collect(Collectors.toList());
        }
        sortUsersByName(result); // Sort filtered results
        return result;
    }

    // === Merge Sort Implementation for Users (Sort by Name) ===
    public void sortUsersByName(List<User> userList) {
        if (userList.size() <= 1) {
            return;
        }
        mergeSort(userList, 0, userList.size() - 1);
    }

    private void mergeSort(List<User> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);

            merge(list, left, mid, right);
        }
    }

    private void merge(List<User> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<User> L = new ArrayList<>();
        List<User> R = new ArrayList<>();

        for (int i = 0; i < n1; ++i)
            L.add(list.get(left + i));
        for (int j = 0; j < n2; ++j)
            R.add(list.get(mid + 1 + j));

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            // Compare Names (Case Insensitive)
            if (L.get(i).getName().compareToIgnoreCase(R.get(j).getName()) <= 0) {
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
}
