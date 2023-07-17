import javax.swing.*;

public class ATMUserInterface {
    private static ATM atm;

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(1000); // Set initial balance here
        atm = new ATM(bankAccount);

        showMenu();
    }

    private static void showMenu() {
        String[] options = {"Check Balance", "Deposit", "Withdraw", "Exit"};

        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Select an option:", "ATM Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    showBalance();
                    break;
                case 1:
                    performDeposit();
                    break;
                case 2:
                    performWithdraw();
                    break;
                default:
                    System.exit(0);
            }
        }
    }

    private static void showBalance() {
        double balance = atm.checkBalance();
        JOptionPane.showMessageDialog(null, "Your balance: Rs." + balance);
    }

    private static void performDeposit() {
        String input = JOptionPane.showInputDialog(null, "Enter the amount to deposit:");
        if (input == null) return;

        try {
            double amount = Double.parseDouble(input);
            atm.deposit(amount);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
        }
    }

    private static void performWithdraw() {
        String input = JOptionPane.showInputDialog(null, "Enter the amount to withdraw:");
        if (input == null) return;

        try {
            double amount = Double.parseDouble(input);
            atm.withdraw(amount);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
        }
    }
}

class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            showMessage("Invalid amount. Please enter a positive value.");
            return;
        }

        if (bankAccount.getBalance() >= amount) {
            bankAccount.withdraw(amount);
            showMessage("Withdrawal successful. Remaining balance: Rs." + bankAccount.getBalance());
        } else {
            showMessage("Insufficient balance. Current balance: Rs." + bankAccount.getBalance());
        }
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            showMessage("Invalid amount. Please enter a positive value.");
            return;
        }

        bankAccount.deposit(amount);
        showMessage("Deposit successful. Current balance: Rs." + bankAccount.getBalance());
    }

    public double checkBalance() {
        return bankAccount.getBalance();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}
