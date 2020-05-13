package services;


import models.Account;
import repository.AccountRepository;

import java.io.Serializable;
import java.util.HashMap;

public class AccountService implements Serializable {

    private final AccountRepository accountRepository;

    public AccountService() {
        accountRepository = new AccountRepository();
    }

    public Account FindAccount(String accountNumber){
        return accountRepository.FindAccount(accountNumber);
    }

    public Account findAccountByUser(String userName){
        return accountRepository.findAccountByUser(userName);
    }

    public void addAccount(Account account){
        accountRepository.addAccount(account);
    }

    public HashMap<String, Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public String createAccount(Account account) {
        return accountRepository.createAccount(account);
    }

    public boolean updateAccount(Account account) {
        return accountRepository.updateAccount(account);
    }

    public String transferFunds(String fromAccountNo, String toAccountNo, double transferAmount) {

        Account fromAccount = FindAccount(fromAccountNo);
        Account toAccount = FindAccount(toAccountNo);

        if (fromAccount == null || toAccount == null) {
            return "One of the accounts no. is wrong";
        }

        String returnMessage = fromAccount.transferFunds(toAccount, transferAmount);

        if(returnMessage.isEmpty()){
            accountRepository.updateAccount(fromAccount);
            accountRepository.updateAccount(toAccount);
            returnMessage = "Account No " + fromAccountNo + " Balance is:" + fromAccount.getBalance()
                          + " and Account No " + toAccountNo + " Balance is:" + toAccount.getBalance();
        }

        return returnMessage;
    }

    public void deposit(String accountNo, double amount) {

        Account account = FindAccount(accountNo);

        if (account == null) {
            return;
        }

        account.deposit(amount);
        accountRepository.updateAccount(account);
    }

    public void withdraw(String accountNo, double amount) {

        Account account = FindAccount(accountNo);

        if (account == null) {
            return;
        }

        account.withdraw(amount);
        accountRepository.updateAccount(account);
    }

    public double getAccountBalance(String accountNo) {

        Account account = FindAccount(accountNo);

        if (account == null) {
            return 0.0;
        }

        double accountBalance = account.getBalance();

        return accountBalance;
    }
}
