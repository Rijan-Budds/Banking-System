import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        Banking.loadAccountsFromFile();
        Scanner scan = new Scanner(System.in);
        Banking banking = new Banking();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Saving accounts before shutdown...");
            Banking.saveAccounts(); 
        }));

        try {
            while (true) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1. Create a new account.");
                System.out.println("2. Deposit into account.");
                System.out.println("3. Withdraw from account.");
                System.out.println("4. View balance.");
                System.out.println("5. Show accounts.");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = getValidIntInput(scan);
                switch (choice) {
                    case 1:
                        banking.createAccount(scan);
                        break;
                    case 2:
                        banking.deposit(scan);
                        break;
                    case 3:
                        banking.withdraw(scan);
                        break;
                    case 4:
                        banking.viewBalance(scan);
                        break;
                    case 5:
                        banking.showAccountHolders();
                        break;
                    case 6:
                        System.out.println("Thank you for using the Banking System!");
                        Banking.saveAccounts(); 
                        return; 
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } finally {
            scan.close(); 
        }
    }

    private static int getValidIntInput(Scanner scan) {
        while (!scan.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scan.next(); 
        }
        return scan.nextInt();
    }
}
