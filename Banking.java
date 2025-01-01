import java.io.*;
import java.util.*;

public class Banking {
    private static final String ACCOUNTS_FILE = "accounts.dat";
    private ArrayList<Account> accounts;
    private int nextAccountNumber;

    public Banking() {
        this.accounts = new ArrayList<>();
        this.nextAccountNumber = 1001;
        loadAccountsFromFile();
    }

    @SuppressWarnings("unchecked")
    public void loadAccountsFromFile() {
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) {
            System.out.println("No saved accounts found. Starting fresh.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object data = ois.readObject();
            
            if (data instanceof AccountData) {
                AccountData accountData = (AccountData) data;
                this.accounts = accountData.getAccounts();
                this.nextAccountNumber = accountData.getNextAccountNumber();
            } else if (data instanceof ArrayList<?>) {
                this.accounts = (ArrayList<Account>) data;
  
                this.nextAccountNumber = accounts.stream()
                    .mapToInt(Account::getAccountNumber)
                    .max()
                    .orElse(1000) + 1;
            }
            System.out.println("Accounts loaded successfully.");
        } catch (InvalidClassException e) {
            System.out.println("Data format has changed. Creating new data file.");
            file.delete();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());

            file.delete();
        }
    }

    public void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
            AccountData data = new AccountData(accounts, nextAccountNumber);
            oos.writeObject(data);
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
            System.err.println("Please ensure you have write permissions in this directory.");
        }
    }

    public void createAccount(Scanner scan) {
        try {
            System.out.print("Enter account holder name: ");
            scan.nextLine();
            String name = scan.nextLine().trim();

            validateName(name);
            Account newAccount = new Account(name, nextAccountNumber++);
            accounts.add(newAccount);
            System.out.println("Account created successfully! Your account number is: " + newAccount.getAccountNumber());
            saveAccounts(); 
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty.");
        }
        if (!name.matches("^[a-zA-Z\\s]+$")) {
            throw new IllegalArgumentException("Account name should only contain letters and spaces.");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Account name is too long. Maximum 50 characters allowed.");
        }
    }

    public void deposit(Scanner scan) {
        try {
            Account account = findAndValidateAccount(scan);
            if (account != null) {
                double amount = getValidAmount(scan, "deposit");
                if (amount > 0) {
                    account.deposit(amount);
                    saveAccounts(); 
                }
            }
        } catch (Exception e) {
            System.out.println("Error processing deposit: " + e.getMessage());
        }
    }

    public void withdraw(Scanner scan) {
        try {
            Account account = findAndValidateAccount(scan);
            if (account != null) {
                double amount = getValidAmount(scan, "withdraw");
                if (amount > 0) {
                    account.withdraw(amount);
                    saveAccounts(); 
                }
            }
        } catch (Exception e) {
            System.out.println("Error processing withdrawal: " + e.getMessage());
        }
    }

    public void viewBalance(Scanner scan) {
        try {
            Account account = findAndValidateAccount(scan);
            if (account != null) {
                System.out.println("\nAccount Details:");
                System.out.println("Account Number: " + account.getAccountNumber());
                System.out.println("Account Holder: " + account.getAccountHolder());
                System.out.printf("Current Balance: $%.2f%n", account.getBalance());
            }
        } catch (Exception e) {
            System.out.println("Error viewing balance: " + e.getMessage());
        }
    }

    public void showAccountHolders() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts have been created.");
            return;
        }

        System.out.println("\nList of account holders:");
        System.out.println("------------------------");
        for (Account account : accounts) {
            System.out.printf("Account #%d: %s (Balance: $%.2f)%n", 
                account.getAccountNumber(), 
                account.getAccountHolder(),
                account.getBalance());
        }
        System.out.println("------------------------");
        System.out.println("Total accounts: " + accounts.size());
    }

    public void showTransactionHistory(Scanner scan) {
        try {
            Account account = findAndValidateAccount(scan);
            if (account != null) {
                account.showTransactionHistory();
            }
        } catch (Exception e) {
            System.out.println("Error viewing transaction history: " + e.getMessage());
        }
    }

    private Account findAndValidateAccount(Scanner scan) {
        while (true) {
            try {
                System.out.print("Enter account number: ");
                int accountNumber = scan.nextInt();
                Account account = findAccount(accountNumber);
                if (account == null) {
                    throw new IllegalArgumentException("Account not found! Please check the account number.");
                }
                return account;
            } catch (InputMismatchException e) {
                System.out.println("Invalid account number format. Please enter a valid number.");
                scan.next(); 
            }
        }
    }

    private double getValidAmount(Scanner scan, String operation) {
        while (true) {
            try {
                System.out.printf("Enter amount to %s: $", operation);
                double amount = scan.nextDouble();
                if (amount <= 0) {
                    throw new IllegalArgumentException("Amount must be greater than zero.");
                }
                if (amount > 1000000) {
                    throw new IllegalArgumentException("Amount exceeds maximum transaction limit of $1,000,000.");
                }
                return amount;
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount format. Please enter a valid number.");
                scan.next(); 
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Account findAccount(int accountNumber) {
        return accounts.stream()
            .filter(account -> account.getAccountNumber() == accountNumber)
            .findFirst()
            .orElse(null);
    }
}