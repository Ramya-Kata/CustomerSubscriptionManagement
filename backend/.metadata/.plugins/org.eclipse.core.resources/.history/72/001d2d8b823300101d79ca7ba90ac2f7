package com.ramyakata.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramyakata.microservice.dao.AccountService;
import com.ramyakata.microservice.entity.Account;
import com.ramyakata.microservice.exception.BankException;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountServiceObj;

	@PostMapping("/add")
	public ResponseEntity<String> addAccount(@RequestBody Account account) throws BankException {
		Boolean isSaved = accountServiceObj.saveAccount(account);
		if (isSaved) {
			return ResponseEntity.status(201).body("Account created successfully.");
		} else {
			return ResponseEntity.status(400).body("Account creation failed.");
		}
	}

	@GetMapping("/search/{accountNumber}")
	public ResponseEntity<Account> getAccountById(@PathVariable String accountNumber) throws BankException {
		Account account = accountServiceObj.getCustomerByAccountNumber(accountNumber);
		if (account != null) {
			return ResponseEntity.ok(account);
		} else {
			return ResponseEntity.status(404).body(null);
		}
	}

	// Transfer Method: Transfer funds from one account to another
	@PostMapping("/transfer")
	public ResponseEntity<String> transferAmount(@RequestParam String fromAccountNumber, @RequestParam Double amount)
			throws BankException {
		try {
			Account fromAccount = accountServiceObj.getCustomerByAccountNumber(fromAccountNumber);
			if (fromAccount == null) {
				return ResponseEntity.status(404).body("Sender account not found.");
			}

			Boolean isTransferred = accountServiceObj.transferAmount(fromAccount, amount);
			if (isTransferred) {
				return ResponseEntity.status(200).body("Amount transferred successfully.");
			} else {
				return ResponseEntity.status(400).body("Amount transfer failed.");
			}
		} catch (BankException e) {
			return ResponseEntity.status(500).body("Error during transaction: " + e.getMessage());
		}
	}

	// Delete Method: Delete an account by account number
	@DeleteMapping("/delete/{accountNumber}")
	public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) throws BankException {
		Account account = accountServiceObj.getCustomerByAccountNumber(accountNumber);
		if (account == null) {
			return ResponseEntity.status(404).body("Account not found.");
		}

		Boolean isDeleted = accountServiceObj.deleteAccount(account);
		if (isDeleted) {
			return ResponseEntity.status(200).body("Account deleted successfully.");
		} else {
			return ResponseEntity.status(400).body("Account deletion failed.");
		}
	}

}
