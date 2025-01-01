import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountHolder;
    private int accountNumber;
    private double balance;
    private List<String> transactionHistory;
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Account(String accountHolder, int accountNumber) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created");
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction(String.format("Deposited: $%.2f", amount));
            System.out.printf("Deposit successful. New balance: $%.2f%n", balance);
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                addTransaction(String.format("Withdrawn: $%.2f", amount));
                System.out.printf("Withdrawal successful. New balance: $%.2f%n", balance);
            } else {
                System.out.printf("Insufficient funds. Current balance: $%.2f%n", balance);
            }
        } else {
            System.out.println("Invalid amount for withdrawal.");
        }
    }

    private void addTransaction(String transaction) {
        String timestamp = LocalDateTime.now().format(formatter);
        transactionHistory.add(String.format("[%s] %s", timestamp, transaction));
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("\nTransaction History for Account #" + accountNumber);
            System.out.println("Account Holder: " + accountHolder);
            System.out.printf("Current Balance: $%.2f%n", balance);
            System.out.println("\nTransactions:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}