package com.example.accountservices.dto;

import com.example.accountservices.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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
    private String username;
    private Set<UserRole> roles;

    // Password excluded from DTO for security purposes
    // Use specific request DTOs for operations requiring password
}
