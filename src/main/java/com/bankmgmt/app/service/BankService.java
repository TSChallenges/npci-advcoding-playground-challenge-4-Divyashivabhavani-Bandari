package com.bankmgmt.app.service;

import com.bankmgmt.app.entity.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BankService {

    private final List<Account> accounts = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1); // Auto-increment ID

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Optional<Account> getAccountById(int id) {
        return accounts.stream().filter(acc -> acc.getId() == id).findFirst();
    }

    public Account createAccount(Account account) {
        account.setId(idGenerator.getAndIncrement()); // Auto-increment ID
        accounts.add(account);
        return account;
    }

    public boolean deleteAccount(int id) {
        return accounts.removeIf(acc -> acc.getId() == id);
    }

    public Optional<Account> deposit(int id, double amount) {
        if (amount <= 0) return Optional.empty();
        
        return getAccountById(id).map(account -> {
            account.setBalance(account.getBalance() + amount);
            return account;
        });
    }

    public Optional<Account> withdraw(int id, double amount) {
        if (amount <= 0) return Optional.empty();

        return getAccountById(id).map(account -> {
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                return account;
            }
            return null; // Insufficient funds
        });
    }

    public boolean transfer(int fromId, int toId, double amount) {
        if (amount <= 0) return false;

        Optional<Account> fromAccount = getAccountById(fromId);
        Optional<Account> toAccount = getAccountById(toId);

        if (fromAccount.isPresent() && toAccount.isPresent() && fromAccount.get().getBalance() >= amount) {
            fromAccount.get().setBalance(fromAccount.get().getBalance() - amount);
            toAccount.get().setBalance(toAccount.get().getBalance() + amount);
            return true;
        }
        return false;
    }
}
