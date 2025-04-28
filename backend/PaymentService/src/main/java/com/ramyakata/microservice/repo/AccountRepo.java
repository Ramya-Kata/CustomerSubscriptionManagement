package com.ramyakata.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ramyakata.microservice.entity.Account;
import com.ramyakata.microservice.exception.BankException;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

	public Account getCustomerByAccountNumber(String accountNumber) throws BankException;

	public Integer deleteByAccountNumber(String accountNumber) throws BankException;
}
