package com.dev.stockmarketsystem.controllers;

import com.dev.stockmarketsystem.services.BalanceCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/balance-cards")
public class BalanceCardController {

    private final BalanceCardService balanceCardService;

    public BalanceCardController(BalanceCardService balanceCardService) {
        this.balanceCardService = balanceCardService;
    }

    // Load balance using a card code
    @GetMapping("/redeem")
    public ResponseEntity<String> redeemBalanceCard(@RequestParam String cardCode, @RequestParam Long userId) {
        try {
            balanceCardService.redeemCard(cardCode, userId);
            return ResponseEntity.ok("Balance loaded successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

