package models;

import java.util.ArrayList;
import java.util.Collection;

public class Account {

    private String type;
    private String accountNumber;
    private double balance;
    private User customer;
    private Collection<AccountEntry> accountEntries = new ArrayList<>();

    public Account(String type, String accountNumber, User customer) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.customer = customer;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Collection<AccountEntry> getAccountEntries() {
        return accountEntries;
    }

    public double getBalance() {
        balance = 0;
        for (AccountEntry entry : accountEntries) {
            balance+=entry.getTransactionAmount();
        }
        return balance;
    }

    public String getType() {
        return type;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount){
        AccountEntry entry = new AccountEntry(this.accountNumber, amount, "deposit");
        accountEntries.add(entry);
    }
    public void withdraw(double amount){
        AccountEntry entry = new AccountEntry(this.accountNumber, -amount, "withdraw");
        accountEntries.add(entry);
    }

    public String transferFunds(Account toAccount, double amount){
        double fromAccountBalance = this.getBalance();
        if (fromAccountBalance < amount) {
            return "{'error': 'Transfer Amount is greater than your balance'}";
        }
        this.withdraw(amount);
        toAccount.deposit(amount);

        return "";
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + getBalance() +
                ", type='" + type + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
