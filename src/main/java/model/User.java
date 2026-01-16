/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Represents a User in the PharmaStock system.
 * Authorized users can be either Admin or Cashier.
 * 
 * @author nityayadav
 */
public class User {

    private final String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password; // Store hashed in real app
    private String role; // "Admin" or "Cashier"
    private String imagePath;

    /**
     * Constructs a new User with the specified details.
     * 
     * @param userId      Unique identifier for the user.
     * @param name        Full name of the user.
     * @param email       User's email address.
     * @param phoneNumber User's contact number.
     * @param password    User's secret password for authentication.
     * @param role        User's access role (Admin/Cashier).
     * @param imagePath   Path to the user's profile image.
     */
    public User(String userId, String name, String email, String phoneNumber, String password, String role,
            String imagePath) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    } // For login check

    public String getImagePath() {
        return imagePath;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPhoneNumber(String newPhone) {
        this.phoneNumber = newPhone;
    }

    public void setRole(String newRole) {
        this.role = newRole;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
