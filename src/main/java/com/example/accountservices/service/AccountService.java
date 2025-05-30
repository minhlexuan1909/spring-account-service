package com.example.accountservices.service;

import com.example.accountservices.dto.AccountDTO;
import com.example.accountservices.entity.Account;
import com.example.accountservices.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public interface AccountService {

    /**
     * Create a new account
     * @param accountDTO the account data to add
     */
    void add(AccountDTO accountDTO);

    /**
     * Update an existing account
     * @param accountDTO the account data to update (ID must be provided)
     */
    void update(AccountDTO accountDTO);

    /**
     * Update account password
     * @param accountDTO containing id, password (old password) and username field containing new password
     */
    void updatePassword(AccountDTO accountDTO);

    /**
     * Delete an account
     * @param id the account ID to delete
     */
    void delete(Long id);

    /**
     * Get all accounts
     * @return list of all accounts
     */
    List<AccountDTO> getAll();

    /**
     * Get account by ID
     * @param id the account ID
     * @return the account
     */
    AccountDTO getOne(Long id);


}

@Transactional
@Service
class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void add(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));

        accountRepository.save(account);

        accountDTO.setId(account.getId());
    }

    @Override
    public void update(AccountDTO accountDTO) {
        Account account = accountRepository.getById(accountDTO.getId());
        if (account != null) {
            modelMapper.typeMap(AccountDTO.class, Account.class)
                    .addMappings(mapper -> mapper.skip(Account::setPassword)).map(accountDTO, account);

            accountRepository.save(account);
        }
    }

    @Override
    public void updatePassword(AccountDTO accountDTO) {
        Account account = accountRepository.getById(accountDTO.getId());
        if (account != null) {
            //account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));
            accountRepository.save(account);
        }
    }

    @Override
    public void delete(Long id) {
        Account account = accountRepository.getById(id);
        if (account != null) {
            accountRepository.delete(account);
        }
    }

    @Override
    public List<AccountDTO> getAll() {
        List<AccountDTO> accountDTOs = new ArrayList<>();

        accountRepository.findAll().forEach((account) -> {
            accountDTOs.add(modelMapper.map(account, AccountDTO.class));
        });

        return accountDTOs;
    }

    @Override
    public AccountDTO getOne(Long id) {
        Account account = accountRepository.getById(id);

        if (account != null) {
            return modelMapper.map(account, AccountDTO.class);
        }

        return null;
    }
}
