package com.bankmgmt.app.service;

import com.bankmgmt.app.entity.Account;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    private final List<Account> accounts = new ArrayList<>();
    private int nextId = 1; // Simple auto-increment ID

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Optional<Account> getAccountById(int id) {
        return accounts.stream().filter(acc -> acc.getId() == id).findFirst();
    }

    public Account createAccount(Account account) {
        account.setId(nextId++);
        accounts.add(account);
        return account;
    }

    public boolean deleteAccount(int id) {
        return accounts.removeIf(acc -> acc.getId() == id);
    }

    public Optional<Account> deposit(int id, double amount) {
        return (amount > 0) ? getAccountById(id).map(acc -> {
            acc.setBalance(acc.getBalance() + amount);
            return acc;
        }) : Optional.empty();
    }

    public Optional<Account> withdraw(int id, double amount) {
        return (amount > 0) ? getAccountById(id).filter(acc -> acc.getBalance() >= amount).map(acc -> {
            acc.setBalance(acc.getBalance() - amount);
            return acc;
        }) : Optional.empty();
    }

    public boolean transfer(int fromId, int toId, double amount) {
        return (amount > 0) && withdraw(fromId, amount).isPresent() && deposit(toId, amount).isPresent();
    }
}
