package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    //getAll
    public Collection<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }


    //getOneById
    public Optional<Transaction> getATransactionById(Long transactionId){
        return transactionRepository.findById(transactionId);
    }


    //deleteOneById
    public void deleteById(Long transactionId){
        transactionRepository.deleteById(transactionId);
    }

    //deleteOneByCbu
    public void deleteByCbu(Long cbu){
        for(Transaction transaction: transactionRepository.findAll()){
            if(transaction.getCbu().equals(cbu)){
                transactionRepository.delete(transaction);
            }
        }
    }

    public Transaction withdraw(Transaction transaction){

        if(transaction.getAmount() <= 0){
            throw new InsufficientFundsException("Saldo insuficiente");
        }

        transaction.setAmount(-transaction.getAmount());

        return transactionRepository.save(transaction);
    }

    public Transaction deposit(Transaction transaction){

        transaction.setAmount(this.applyDescount(transaction.getAmount()));
        if(transaction.getAmount() <= 0){
            throw new DepositNegativeSumException("Error suma negativa");
        }
        return transactionRepository.save(transaction);
    }

    public Double applyDescount(Double sum){

        if (sum >= 2000 && sum <= 5000){
            sum *= 1.1;
        }else if(sum > 5000){
            sum += 500;
        }
        return sum;
    }
}