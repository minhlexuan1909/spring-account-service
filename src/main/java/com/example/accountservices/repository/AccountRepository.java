package com.example.accountservices.repository;

import com.example.accountservices.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    /**
     * Find an account by its username
     * 
     * @param username the username to search for
     * @return an Optional containing the account if found
     */
    Optional<Account> findByUsername(String username);
    
    /**
     * Check if an account exists with the given username
     * 
     * @param username the username to check
     * @return true if an account with the username exists
     */
    boolean existsByUsername(String username);
}
