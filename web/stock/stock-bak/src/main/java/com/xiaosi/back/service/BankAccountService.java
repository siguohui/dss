package com.xiaosi.back.service;

import com.xiaosi.back.entity.BankAccount;
import com.xiaosi.back.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Transactional
    public BankAccount deposit(Long accountId, BigDecimal amount) throws InterruptedException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        Thread.sleep(10000);
        bankAccount.setBalance(bankAccount.getBalance().add(amount));
        return bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public BankAccount deposit1(Long accountId, BigDecimal amount) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        bankAccount.setBalance(bankAccount.getBalance().add(amount));
        return bankAccountRepository.save(bankAccount);
    }
}
