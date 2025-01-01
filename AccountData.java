import java.io.Serializable;
import java.util.ArrayList;

class AccountData implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Account> accounts;
    private int nextAccountNumber;

    public AccountData(ArrayList<Account> accounts, int nextAccountNumber) {
        this.accounts = accounts;
        this.nextAccountNumber = nextAccountNumber;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public int getNextAccountNumber() {
        return nextAccountNumber;
    }
}