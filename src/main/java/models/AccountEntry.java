package models;

import java.time.LocalDate;

public class AccountEntry {
    private String accountNumber;
    private LocalDate date;
    private double transactionAmount;
    private String description;

    public AccountEntry(String accountNumber, double transactionAmount, String description){
        this.accountNumber= accountNumber;
        this.date= LocalDate.now();
        this.transactionAmount= transactionAmount;
        this.description = description;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                ", account=" + accountNumber +
                ", date=" + date +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
