package com.dev.stockmarketsystem.services;

import com.dev.stockmarketsystem.models.Portfolio;
import com.dev.stockmarketsystem.models.Role;
import com.dev.stockmarketsystem.models.User;
import com.dev.stockmarketsystem.repositories.PortfolioRepository;
import com.dev.stockmarketsystem.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PortfolioRepository portfolioRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create new user with default role and portfolio
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        user.setRole(user.getRole() != null ? user.getRole() : Role.USER); // Set default role to USER
        user.setEnabled(true);
        User savedUser = userRepository.save(user);

        // Create and associate a portfolio for the user
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(savedUser);
        portfolio.setBalance(0.0); // Default balance
        portfolioRepository.save(portfolio);

        return savedUser;
    }

    // Search for a user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Search for a user by id
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User userDetails) {
        // Find the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        // Update user details
        if (userDetails.getUsername() != null) {
            user.setUsername(userDetails.getUsername());
        }
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
        if (userDetails.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        if (userDetails.isEnabled() != user.isEnabled()) {
            user.setEnabled(userDetails.isEnabled());
        }

        // Save and return the updated user
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}
