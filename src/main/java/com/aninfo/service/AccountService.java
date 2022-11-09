package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public TransactionService transactionService;
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);

        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {

        Double count = 0.0;
        if (sum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }else if(sum>=2000 && sum<=5000){
            count = sum*0.1;
        }else if(sum>5000){
            count = 500.0;
        }
        Account account = accountRepository.findAccountByCbu(cbu);
        if(account == null){
            throw new InvalidTransactionTypeException("no existe transaccion");
        }
        account.setBalance(account.getBalance() + sum + count);
        accountRepository.save(account);

        return account;
    }

    public Transaction newAccountTransactionWithdraw(Transaction transaction){
        Account account = accountRepository.findAccountByCbu(transaction.getCbu());
        if(account == null){
            throw new InvalidTransactionTypeException("no existe transaccion para retiro");
        }
        withdraw(account.getCbu(),transaction.getAmount());

        return transactionService.transactionWithdraw(transaction);
    }

    public Transaction newAccountTransactionDeposit(Transaction transaction){
        Double count = 0.0;
        if(transaction.getAmount() <= 0){
            throw new DepositNegativeSumException("no se puede depositar negativo");
        }
        else if(transaction.getAmount() >= 2000 && transaction.getAmount() <= 5000){
            count = transaction.getAmount()*0.1;
        }else if(transaction.getAmount() > 5000){
            count = 500.00;
        }
        Account account = accountRepository.findAccountByCbu(transaction.getCbu());
        if(account == null){
            throw new InvalidTransactionTypeException("no existe transaccion");
        }
        deposit(account.getCbu(),transaction.getAmount()+count);

        return transactionService.transactionDeposit(transaction);
    }

    public TransactionService getTransactionService(){
        return transactionService;
    }

}