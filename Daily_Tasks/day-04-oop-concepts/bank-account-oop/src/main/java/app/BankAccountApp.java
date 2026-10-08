package app;

import model.BankAccount;
import service.BankAccountService;

public class BankAccountApp {

    public static void main(String[] args) {

        BankAccountService service = new BankAccountService();

        // Three constructor examples
        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount("ACC102", "Sabarish");
        BankAccount account3 = new BankAccount("ACC103", "Sabarish", 10000.0);

        System.out.println("===== BANK ACCOUNT DEMO =====");

        service.displayAccount(account3);

        System.out.println("\nDepositing INR 2000...");
        service.deposit(account3, 2000);

        service.displayAccount(account3);

        System.out.println("\nWithdrawing INR 1500...");
        service.withdraw(account3, 1500);

        service.displayAccount(account3);

        System.out.println("\nTotal accounts created: "
                + BankAccount.getAccountCount());

        System.out.println("\n===== EQUALS / HASHCODE DEMO =====");

        BankAccount anotherAccount =
                new BankAccount("ACC103", "Another Person", 500);

        System.out.println("account3.equals(anotherAccount): "
                + account3.equals(anotherAccount));

        System.out.println("account3.hashCode(): "
                + account3.hashCode());

        System.out.println("anotherAccount.hashCode(): "
                + anotherAccount.hashCode());
    }
}