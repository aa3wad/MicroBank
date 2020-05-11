package services;

import repository.TransactionRepository;

public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(){
        transactionRepository= new TransactionRepository();
    }

}