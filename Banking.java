import java.util.ArrayList;
import java.util.Scanner;

public class Banking {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static int accountCounter = 1001;

    public static void createAccount(Scanner scan) {
        System.out.print("Enter account holder name: ");
        scan.nextLine(); 
        String name = scan.nextLine();
        Account newAccount = new Account(name, accountCounter++);
        accounts.add(newAccount);
        System.out.println("Account created successfully! Your account number is: " + newAccount.getAccountNumber());
    }

    public static void deposit(Scanner scan) {
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

    public static void withdraw(Scanner scan) {
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

    public static void viewBalance(Scanner scan) {
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
