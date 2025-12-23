/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Transaction;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nityayadav
 */
public class TransactionController {

    private static List<Transaction> transactions = new ArrayList<>();

    public TransactionController() {
        if (transactions.isEmpty()) {
            transactions.add(new Transaction("TRX988", "C324", LocalDate.now(), LocalTime.now(), 32000.0));
            // Add more sample transactions...
        }
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public int getTotalTransactions() {
        return transactions.size();
    }

    public double getTotalSales() {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }
}
