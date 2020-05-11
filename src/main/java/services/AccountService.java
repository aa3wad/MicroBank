package services;


import models.Account;
import repository.AccountRepository;

import java.io.Serializable;
import java.util.HashMap;

public class AccountService implements Serializable {

    private AccountRepository accountRepository;

    public AccountService() {
        accountRepository = new AccountRepository();
    }

    public Account FindAccount(String accountNumber){
        return accountRepository.FindAccount(accountNumber);
    }

    public void addAccount(Account account){
        accountRepository.addAccount(account);
    }

    public HashMap<String, Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public boolean createAccount(Account account) {

        return accountRepository.createAccount(account);
    }

    public boolean updateAccount(Account account) {
        return accountRepository.updateAccount(account);
    }

}
