package com.example.accountservices.controller;

import com.example.accountservices.client.NotificationService;
import com.example.accountservices.client.StatisticService;
import com.example.accountservices.dto.AccountDTO;
import com.example.accountservices.dto.MessageDTO;
import com.example.accountservices.dto.StatisticDTO;
import com.example.accountservices.service.AccountService;
import jakarta.annotation.security.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AccountService accountService;
    private final StatisticService statisticService;
    private final NotificationService notificationService;

    public AccountController(AccountService accountService, StatisticService statisticService, NotificationService notificationService) {
        this.accountService = accountService;
        this.statisticService = statisticService;
        this.notificationService = notificationService;
    }

    // add new
    @PostMapping("/account")
    @PermitAll
    public AccountDTO addAccount(@RequestBody AccountDTO accountDTO) {
        accountService.add(accountDTO);

        statisticService.add(new StatisticDTO("Account " + accountDTO.getUsername() + " is created", new Date()));

        //send notificaiton
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFrom("minhlx.springtest@gmail.com");
        messageDTO.setTo(accountDTO.getUsername());//username is email
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setSubject("Welcome to MinhLX Test");
        messageDTO.setContent("Hello there!");

        notificationService.sendNotification(messageDTO);

        return accountDTO;
    }

    // get all
    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('SCOPE_read') && hasRole('ADMIN')")
    public List<AccountDTO> getAll() {
        logger.info("AccountService Controller: get all accounts");

        statisticService.add(new StatisticDTO("Get all accounts", new Date()));

        return accountService.getAll();
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> get(@PathVariable(name = "id") Long id) {
        return Optional.of(new ResponseEntity<AccountDTO>(accountService.getOne(id), HttpStatus.OK))
                .orElse(new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        statisticService.add(new StatisticDTO("Delete account id " + id, new Date()));

        accountService.delete(id);
    }

    @PutMapping("/account")
    public void update(@RequestBody AccountDTO accountDTO) {
        statisticService.add(new StatisticDTO("Update account: " + accountDTO.getUsername(), new Date()));

        accountService.update(accountDTO);
    }
}
