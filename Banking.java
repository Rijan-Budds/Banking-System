import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Banking {
    private static ArrayList<Account> accounts = new ArrayList<>();

    private static int accountCounter = 1001;

    public static void saveAccounts(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accounts.dat"))){
            oos.writeObject(accounts);
            System.out.println("Accounts have been saved successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving account." + e.getMessage());
        }
    }

@SuppressWarnings("unchecked")
public static void loadAccountsFromFile() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("accounts.dat"))) {
        accounts = (ArrayList<Account>) ois.readObject();
        System.out.println("Accounts loaded successfully.");
    } catch (FileNotFoundException e) {
        System.out.println("No saved accounts found.");
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error loading accounts: " + e.getMessage());
    }
}



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
