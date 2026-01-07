/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Transaction;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author nityayadav
 */
public class TransactionController {

    private static List<Transaction> transactions = new LinkedList<>();

    public TransactionController() {
        if (transactions.isEmpty()) {
            transactions.add(new Transaction("TRX988", "C324", LocalDate.now(), LocalTime.now(), 550.0));

            // Additional Transactions
            transactions.add(
                    new Transaction("TRX989", "C8584", LocalDate.now().minusDays(1), LocalTime.of(10, 30), 120.0));
            transactions.add(
                    new Transaction("TRX990", "C8585", LocalDate.now().minusDays(2), LocalTime.of(14, 15), 340.0));
            transactions.add(new Transaction("TRX991", "C8583", LocalDate.now(), LocalTime.of(9, 45), 85.0));
            transactions.add(
                    new Transaction("TRX992", "C8586", LocalDate.now().minusDays(5), LocalTime.of(16, 20), 210.0));
        }
    }

    public List<Transaction> getAllTransactions() {
        return new LinkedList<>(transactions);
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

    public int getTotalQuantitySold() {
        return transactions.stream()
                .mapToInt(t -> t.getProductIds().size())
                .sum();
    }

    public double getTodaySales() {
        return transactions.stream()
                .filter(t -> t.getDate().equals(LocalDate.now()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public int getTodayTransactionsCount() {
        return (int) transactions.stream()
                .filter(t -> t.getDate().equals(LocalDate.now()))
                .count();
    }

    public List<String> getTopSellingProductIds(int limit) {
        return transactions.stream()
                .filter(t -> t.getDate().equals(LocalDate.now()))
                .flatMap(t -> t.getProductIds().stream())
                .collect(java.util.stream.Collectors.groupingBy(id -> id, java.util.stream.Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(limit)
                .map(java.util.Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toList());
    }
}
