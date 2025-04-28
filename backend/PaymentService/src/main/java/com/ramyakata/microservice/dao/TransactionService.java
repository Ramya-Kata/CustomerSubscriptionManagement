package com.ramyakata.microservice.dao;

import com.ramyakata.microservice.entity.Account;
import com.ramyakata.microservice.exception.BankException;

public interface TransactionService {

	public Boolean deposit(Account account, Double amount) throws BankException;

	public Boolean credit(Account account, Double amount) throws BankException;
}
