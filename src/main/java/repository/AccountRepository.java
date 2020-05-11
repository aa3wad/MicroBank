package repository;

import models.Account;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class AccountRepository implements Serializable {
    private HashMap<String , Account> accounts;

    public AccountRepository() {
        prepareAccounts();
    }

    public void prepareAccounts() {
        accounts = new HashMap<>();
        List<Account> accountList = Arrays.asList(
                new Account(1000, "Saving", "111"),
                new Account(2000, "Saving", "222"),
                new Account(3000, "Saving", "333"),
                new Account(4000, "Checking", "444"),
                new Account(5000, "Checking", "555"),
                new Account(6000, "Checking", "666"));

//        accounts = (HashMap<String , Account>) accountList.stream().collect(
//                Collectors.toMap(Account::getAccountNumber, account -> account))
//                ;
        for (Account account : accountList) {
            accounts.put(account.getAccountNumber(), account);
        }
    }

    public Account FindAccount(String accountNumber){
        return accounts.get(accountNumber);
    }

    public void addAccount(Account account){
        accounts.put(account.getAccountNumber(),account);
    }

    public HashMap<String, Account> getAllAccounts() {
        return accounts;
    }

    public boolean createAccount(Account account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            return false;
        }
        else {
            accounts.put(account.getAccountNumber(), account);
            return true;
        }

    }

    public boolean updateAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        return true;
    }
}

