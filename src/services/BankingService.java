package services;

import models.Account;

import java.util.HashMap;
import java.util.Map;

public class BankingService {
    private Map<String, Account> accounts = new HashMap<>();

    public void createAccount(String accountNumber, String accountHolder, double initialDeposit) {
        Account account = new Account(accountNumber, accountHolder, initialDeposit);
        accounts.put(accountNumber, account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean transfer(String fromAccount, String toAccount, double amount) {
        Account sender = accounts.get(fromAccount);
        Account receiver = accounts.get(toAccount);

        if (sender != null && receiver != null && sender.withdraw(amount)) {
            receiver.deposit(amount);
            System.out.println("Transfer successful!");
            return true;
        }
        System.out.println("Transfer failed!");
        return false;
    }
}
