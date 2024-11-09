package main;

import java.util.Scanner;
import models.Account;
import services.BankingService;

public class BankingSystemMain {

    private static BankingService bankingService = new BankingService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Banking System!");

        // Create some demo accounts for login
        bankingService.createAccount("12345", "Alice", 1000);
        bankingService.createAccount("67890", "Bob", 500);

        // Run login and main menu loop
        while (true) {
            Account account = login();
            if (account != null) {
                showMainMenu(account);
            } else {
                System.out.println("Login failed. Try again.");
            }
        }
    }

    private static Account login() {
        System.out.print("\nEnter account number to login: ");
        String accountNumber = scanner.next();
        Account account = bankingService.getAccount(accountNumber);

        if (account != null) {
            System.out.println("\nLogin successful! Welcome, " + account.getAccountHolder() + "!");
        } else {
            System.out.println("\nAccount not found.");
        }
        return account;
    }

    private static void showMainMenu(Account account) {
        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. View Balance");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleDeposit(account);
                    break;
                case 2:
                    handleWithdraw(account);
                    break;
                case 3:
                    handleTransfer(account);
                    break;
                case 4:
                    System.out.println("\nCurrent balance: $" + account.getBalance());
                    break;
                case 5:
                    System.out.println("\nLogging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleDeposit(Account account) {
        System.out.print("\nEnter deposit amount: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
        System.out.println("Deposit successful. New balance: $" + account.getBalance());
    }

    private static void handleWithdraw(Account account) {
        System.out.print("\nEnter withdrawal amount: ");
        double amount = scanner.nextDouble();
        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful. New balance: $" + account.getBalance());
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }

    private static void handleTransfer(Account fromAccount) {
        System.out.print("\nEnter recipient account number: ");
        String toAccountNumber = scanner.next();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();

        if (bankingService.transfer(fromAccount.getAccountNumber(), toAccountNumber, amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed. Check account number and balance.");
        }
    }
}
