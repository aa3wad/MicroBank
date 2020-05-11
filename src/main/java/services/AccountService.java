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



}
