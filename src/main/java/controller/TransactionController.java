/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Transaction;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nityayadav
 */
public class TransactionController {

    private static List<Transaction> transactions = new LinkedList<>();

    public TransactionController() {
        if (transactions.isEmpty()) {
            Transaction t1 = new Transaction("TRX988", "C324", LocalDate.now(), LocalTime.now(), 550.0);
            t1.addProduct("P0321");
            transactions.add(t1);

            // Additional Transactions
            Transaction t2 = new Transaction("TRX989", "C8584", LocalDate.now().minusDays(1), LocalTime.of(10, 30),
                    120.0);
            t2.addProduct("P0685");
            transactions.add(t2);

            Transaction t3 = new Transaction("TRX990", "C8585", LocalDate.now().minusDays(2), LocalTime.of(14, 15),
                    340.0);
            t3.addProduct("P1001");
            transactions.add(t3);

            Transaction t4 = new Transaction("TRX991", "C8583", LocalDate.now(), LocalTime.of(9, 45), 85.0);
            t4.addProduct("P1002");
            transactions.add(t4);

            Transaction t5 = new Transaction("TRX992", "C8586", LocalDate.now().minusDays(5), LocalTime.of(16, 20),
                    210.0);
            t5.addProduct("P1003");
            transactions.add(t5);
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
        double total = 0;
        for (int i = 0; i < transactions.size(); i++) {
            total += transactions.get(i).getAmount();
        }
        return total;
    }

    public int getTotalQuantitySold() {
        int total = 0;
        for (int i = 0; i < transactions.size(); i++) {
            total += transactions.get(i).getProductIds().size();
        }
        return total;
    }

    public double getTodaySales() {
        double total = 0;
        LocalDate today = LocalDate.now();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            if (t.getDate().equals(today)) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public int getTodayTransactionsCount() {
        int count = 0;
        LocalDate today = LocalDate.now();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getDate().equals(today)) {
                count++;
            }
        }
        return count;
    }

    public List<String> getTopSellingProductIds(int limit) {
        Map<String, Long> counts = new HashMap<>();
        LocalDate today = LocalDate.now();

        // Count occurrences manually
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            if (t.getDate().equals(today)) {
                List<String> productIds = t.getProductIds();
                for (int j = 0; j < productIds.size(); j++) {
                    String id = productIds.get(j);
                    counts.put(id, counts.getOrDefault(id, 0L) + 1);
                }
            }
        }

        // Sort manually
        List<Map.Entry<String, Long>> list = new ArrayList<>(counts.entrySet());
        // Selection Sort O(N^2) - demonstrating manual algorithm
        for (int i = 0; i < list.size() - 1; i++) {
            int maxIdx = i;
            for (int k = i + 1; k < list.size(); k++) {
                if (list.get(k).getValue() > list.get(maxIdx).getValue()) {
                    maxIdx = k;
                }
            }
            // Swap
            Map.Entry<String, Long> temp = list.get(maxIdx);
            list.set(maxIdx, list.get(i));
            list.set(i, temp);
        }

        // Limit results
        List<String> result = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < list.size() && count < limit; i++) {
            result.add(list.get(i).getKey());
            count++;
        }

        return result;
    }
}
