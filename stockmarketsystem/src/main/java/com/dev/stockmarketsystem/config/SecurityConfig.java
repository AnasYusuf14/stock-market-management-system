package com.dev.stockmarketsystem.config;

import com.dev.stockmarketsystem.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authorization configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable() // Disable CSRF for API-based applications
                .authorizeRequests()
                .requestMatchers("/api/stocks/active").hasAnyRole("ADMIN", "USER") // Active stocks
                .requestMatchers("/api/stocks/deactivate/**").hasRole("ADMIN") // Deactivate stocks
                .requestMatchers("/api/users/**").hasRole("ADMIN") // Manage users
                .anyRequest().authenticated() // All other endpoints require authentication
                .and()
                .httpBasic() // Enable Basic Authentication
                .and()
                .build();
    }

    // Authentication Manager configuration
    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            UserDetailsServiceImpl userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}
