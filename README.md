# PharmaStock - Pharmacy Management System

PharmaStock is a comprehensive pharmacy management system designed to streamline the operations of a modern pharmacy. It provides robust features for managing inventory, tracking sales transactions, managing customers, and controlling user access with different roles (Admin and Cashier).

## 🚀 Key Features

- **Inventory Management**: Add, update, view, and search for pharmaceutical products.
- **Transaction Processing**: Handle sales transactions efficiently with real-time stock updates.
- **Customer Management**: Maintain a database of customers and their purchase history.
- **User Authentication & Role-Based Access**: Secure login system with distinct dashboards for Administrators and Cashiers.
- **Dashboard Analytics**: Visual representation of sales, low-stock alerts, and activity logs.
- **Advanced Search & Sorting**: Quick and efficient data retrieval using custom-implemented algorithms.
- **Undo Functionality**: Support for undoing recent actions in inventory management.

## 🛠️ Technology Stack

- **Language**: Java 17
- **UI Framework**: Java Swing (Desktop GUI)
- **Build Tool**: Maven
- **Design Pattern**: Model-View-Controller (MVC)

## 🏗️ Project Structure

```bash
PharmaStock/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/mycompany/pharmastock/ # Main Entry Point
│   │   │   ├── controller/               # MVC Controllers
│   │   │   ├── model/                    # Data Models
│   │   │   ├── View/                     # UI Panels & Frames
│   │   │   └── images/                   # System Icons & Assets
│   │   └── resources/                    # Static Resources
├── pom.xml                                # Maven Dependencies
└── README.md                              # Project Documentation
```

## 🧠 Data Structures & Algorithms

This project serves as coursework for Data Structures and Algorithms, featuring custom implementations:

- **Data Structures**:
  - `Stack`: Used for the Undo functionality.
  - `Queue`: Implemented for the Activity Log system.
  - `LinkedList`: Used for managing transaction records.
- **Algorithms**:
  - **Sorting**: Quick Sort, Merge Sort, and Selection Sort for product and transaction indexing.
  - **Searching**: Linear Search and Binary Search for efficient data lookup.

## 🏁 Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Apache Maven

### Installation & Running

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd PharmaStock
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn exec:java
   ```

## 📸 Screenshots

*(Add your project screenshots here to showcase the stunning UI!)*

---
Developed as part of the Data Structure and Algorithms Coursework.
