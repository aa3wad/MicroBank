package services;

import models.Transaction;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(){
        transactionRepository= new TransactionRepository();
    }

}