import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Banking banking = new Banking();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Saving accounts before shutdown...");
            banking.saveAccounts();
        }));

        try {
            while (true) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1. Create a new account");
                System.out.println("2. Deposit into account");
                System.out.println("3. Withdraw from account");
                System.out.println("4. View balance");
                System.out.println("5. Show accounts");
                System.out.println("6. View transactions");
                System.out.println("7. Delete account.");
                System.out.println("8. Exit");
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
                        banking.showTransactionHistory(scan);
                        break;
                    case 7:
                        banking.deleteAccount(scan);
                        break;
                    case 8:
                        System.out.println("Thank you for using the Banking System!");
                        banking.saveAccounts();
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
        while (true) {
            try {
                return scan.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine();
            }
        }
    }
}