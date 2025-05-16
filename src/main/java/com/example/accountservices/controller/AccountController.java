package com.example.accountservices.controller;

import com.example.accountservices.client.NotificationService;
import com.example.accountservices.client.StatisticService;
import com.example.accountservices.dto.AccountDTO;
import com.example.accountservices.dto.StatisticDTO;
import com.example.accountservices.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final StatisticService statisticService;

    public AccountController(AccountService accountService, StatisticService statisticService) {
        this.accountService = accountService;
        this.statisticService = statisticService;
    }
    // add new
    @PostMapping("/account")
    public AccountDTO addAccount(@RequestBody AccountDTO accountDTO) {
        accountService.add(accountDTO);
        // Send notification using StatisticService with Feign client
        statisticService.add(new StatisticDTO("Account " + accountDTO.getUsername() + " is created at", new Date()));
        return accountDTO;
    }

    // get all
    @GetMapping("/accounts")
    public List<AccountDTO> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> get(@PathVariable(name = "id") Long id) {
        return Optional.of(new ResponseEntity<AccountDTO>(accountService.getOne(id), HttpStatus.OK))
                .orElse(new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        accountService.delete(id);
    }

    @PutMapping("/account")
    public void update(@RequestBody AccountDTO accountDTO) {
        accountService.update(accountDTO);
    }
}
