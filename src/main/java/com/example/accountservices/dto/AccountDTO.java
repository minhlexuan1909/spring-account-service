package com.example.accountservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object for Account information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String name;
    private String password;
    private String username;
    private Set<String> roles;

    // Password excluded from DTO for security purposes
    // Use specific request DTOs for operations requiring password
}
