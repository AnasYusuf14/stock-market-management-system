package com.dev.stockmarketsystem.controllers;

import com.dev.stockmarketsystem.models.Transaction;
import com.dev.stockmarketsystem.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Buy Stock
    @PostMapping("/buy")
    public ResponseEntity<Transaction> buyStock(
            @RequestParam Long userId,
            @RequestParam Long stockId,
            @RequestParam int quantity) {
        Transaction transaction = transactionService.buyStock(userId, stockId, quantity);
        return ResponseEntity.ok(transaction);
    }

    // Sell Stock
    @PostMapping("/sell")
    public ResponseEntity<Transaction> sellStock(
            @RequestParam Long userId,
            @RequestParam Long stockId,
            @RequestParam int quantity) {
        Transaction transaction = transactionService.sellStock(userId, stockId, quantity);
        return ResponseEntity.ok(transaction);
    }

    //  View Transactions by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }
}

