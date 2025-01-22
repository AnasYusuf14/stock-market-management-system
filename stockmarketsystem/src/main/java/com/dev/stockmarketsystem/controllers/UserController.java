package com.dev.stockmarketsystem.controllers;

import com.dev.stockmarketsystem.models.User;
import com.dev.stockmarketsystem.models.Role;
import com.dev.stockmarketsystem.services.BalanceCardService;
import com.dev.stockmarketsystem.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final BalanceCardService balanceCardService;

    public UserController(UserService userService, BalanceCardService balanceCardService) {
        this.userService = userService;
        this.balanceCardService = balanceCardService;
    }

    // 1. Create a new user (default role: USER)
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setRole(Role.USER); // Default role is USER
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // 2. Admin creates a new user with a specified role
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<User> createUserByAdmin(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // 3. Search for a user by username
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // 4. Display all users (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 5. Update user details (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // 6. Delete a user (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    // 7. Admin assigns balance to a user
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/balance/{userId}")
    public ResponseEntity<String> assignBalanceToUser(@PathVariable Long userId, @RequestBody double balance) {
        balanceCardService.assignBalanceToUser(userId, balance);
        return ResponseEntity.ok("Balance assigned successfully!");
    }

    // 8. Retrieve a user's current balance
    @GetMapping("/{userId}/balance")
    public ResponseEntity<Double> getUserBalance(@PathVariable Long userId) {
        double balance = balanceCardService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }
}
