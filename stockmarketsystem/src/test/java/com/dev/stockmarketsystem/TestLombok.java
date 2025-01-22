package com.dev.stockmarketsystem;
import com.dev.stockmarketsystem.models.User;
import com.dev.stockmarketsystem.models.Role;

public class TestLombok {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        user.setRole(Role.ADMIN);

        System.out.println(user.getUsername());
    }
}
