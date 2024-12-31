import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private String accountHolder;
    private int accountNumber;
    private double balance;

    public Account(String accountHolder, int accountNumber) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
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
            System.out.println("Deposit was successful.");
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal was successful.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            System.out.println("Invalid amount.");
        }
    }
}

public class Banking {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static int accountCounter = 1001;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1. Create a new account.");
                System.out.println("2. Deposit into account.");
                System.out.println("3. Withdraw from account.");
                System.out.println("4. View balance.");
                System.out.println("5. Exit.");
                System.out.print("Enter your choice: ");
                int choice = scan.nextInt();

                switch (choice) {
                    case 1:
                        createAccount(scan);
                        break;
                    case 2:
                        deposit(scan);
                        break;
                    case 3:
                        withdraw(scan);
                        break;
                    case 4:
                        viewBalance(scan);
                        break;
                    case 5:
                        System.out.println("Thank you for using the Banking System!");
                        return; // Exit the program
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } finally {
            scan.close(); // Ensure scanner is closed
        }
    }

    private static void createAccount(Scanner scan) {
        System.out.print("Enter account holder name: ");
        scan.nextLine(); // Consume the newline
        String name = scan.nextLine();
        Account newAccount = new Account(name, accountCounter++);
        accounts.add(newAccount);
        System.out.println("Account created successfully! Your account number is: " + newAccount.getAccountNumber());
    }

    private static void deposit(Scanner scan) {
        System.out.print("Enter account number: ");
        int accountNumber = scan.nextInt();
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = scan.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    private static void withdraw(Scanner scan) {
        System.out.print("Enter account number: ");
        int accountNumber = scan.nextInt();
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter amount to withdraw: ");
            double amount = scan.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    private static void viewBalance(Scanner scan) {
        System.out.print("Enter account number: ");
        int accountNumber = scan.nextInt();
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.println("Account Holder: " + account.getAccountHolder());
            System.out.println("Current Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found!");
        }
    }

    private static Account findAccount(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }
}
