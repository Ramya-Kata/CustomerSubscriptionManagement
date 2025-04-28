package com.ramyakata.microservice.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Description: Entity class representing a bank account in the system. The
 * class is mapped to the "Bank_Account" table in the database. This entity
 * contains information about a bank account, including:
 * <ul>
 * <li>Account ID</li>
 * <li>Account Type (CREDIT or DEBIT)</li>
 * <li>Account Balance</li>
 * <li>Account Holder's Name</li>
 * <li>Account Number</li>
 * <li>Routing Number</li>
 * <li>Expiry Date</li>
 * <li>Account Creation Date</li>
 * </ul>
 * 
 *
 */
@Entity
@Table(name = "accounts", indexes = { @Index(name = "idx_account_number", columnList = "account_number"),
		@Index(name = "idx_routing_number", columnList = "routing_number") })
public class Account {

	/**
	 * Description: The unique identifier for the account. Automatically generated
	 * based on sequence. This field is mapped to the "account_id" column in the
	 * database.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
	@SequenceGenerator(name = "account_seq", sequenceName = "account_id_seq", initialValue = 100, allocationSize = 1)
	@Column(name = "account_id")
	private Long accountId;

	/**
	 * Description: The type of the bank account. It could either be a CREDIT or
	 * DEBIT account. This field is mapped to the "account_type" column in the
	 * database.
	 */
	@Column(name = "account_type")
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	/**
	 * Description: The current balance of the account. This field is mapped to the
	 * "balance" column in the database.
	 */

	@Column(name = "balance")
	private Double balance;

	/**
	 * Description: The name of the account holder. This field is mapped to the
	 * "card_holder" column in the database.
	 */

	@Column(name = "card_holder")
	private String accountHolderName;

	/**
	 * Description: The unique account number associated with the account. This
	 * field is mapped to the "account_number" column in the database.
	 */

	@Column(name = "account_number")
	private String accountNumber;

	/**
	 * Description: The routing number for the bank associated with the account.
	 * This field is mapped to the "routing_number" column in the database.
	 */

	@Column(name = "routing_number")
	private String routingNumber;

	/**
	 * Description: The expiry date for the bank account (for debit/credit cards).
	 * This field is mapped to the "expiry_date" column in the database.
	 */

	@Column(name = "expiry_date")
	private LocalDate expiryDate;

	/**
	 * Description: The date when the bank account was created. This field is mapped
	 * to the "created_date" column in the database.
	 */

	@Column(name = "created_date")
	private LocalDate openDate;

	/**
	 * Description: Returns the account ID.
	 * 
	 * @return the account ID
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * Description: The set of transactions associated with this account. This is a
	 * one-to-many relationship where one account can have many transactions. The
	 * "account" field in the Transaction entity is mapped to this collection.
	 */

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Transaction> transactions = new HashSet<Transaction>();

	/**
	 * Description:
	 */

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Description: Sets the set of transactions associated with this account.
	 * 
	 * @param transactions the set of transactions to set
	 */

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	/**
	 * Description: Sets the account ID before persisting the entity. If the account
	 * ID is not provided, it is automatically generated in the format "BANKxxxxx".
	 */

	/**
	 * Description: Returns the type of the bank account.
	 * 
	 * @return the account type (CREDIT or DEBIT)
	 */

	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * Description:Sets the type of the bank account.
	 * 
	 * @param accountType the account type (CREDIT or DEBIT) to set
	 */

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	/**
	 * Description:Returns the current balance of the bank account.
	 * 
	 * @return the balance
	 */

	public Double getBalance() {
		return balance;
	}

	/**
	 * Description:Sets the current balance of the bank account.
	 * 
	 * @param balance the balance to set
	 */

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * Description:Returns the name of the account holder.
	 * 
	 * @return the account holder's name
	 */

	public String getAccountHolderName() {
		return accountHolderName;
	}

	/**
	 * Description:Sets the name of the account holder.
	 * 
	 * @param accountHolderName the account holder's name to set
	 */

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	/**
	 * Description:Returns the account number associated with the bank account.
	 * 
	 * @return the account number
	 */

	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Description:Sets the account number associated with the bank account.
	 * 
	 * @param accountNumber the account number to set
	 */

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * Description:Returns the routing number associated with the bank account.
	 * 
	 * @return the routing number
	 */

	public String getRoutingNumber() {
		return routingNumber;
	}

	/**
	 * Description:Sets the routing number associated with the bank account.
	 * 
	 * @param routingNumber the routing number to set
	 *
	 */

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	/**
	 * Description:Returns the expiry date of the bank account (for debit/credit
	 * cards).
	 * 
	 * @return the expiry date
	 */

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Description:Sets the expiry date of the bank account (for debit/credit
	 * cards).
	 * 
	 * @param expiryDate the expiry date to set
	 */

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Description:Returns the date when the bank account was created.
	 * 
	 * @return the account creation date
	 */

	public LocalDate getOpenDate() {
		return openDate;
	}

	/**
	 * Description:Sets the date when the bank account was created.
	 * 
	 * @param openDate the account creation date to set
	 */

	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountType=" + accountType + ", balance=" + balance
				+ ", accountHolderName=" + accountHolderName + ", accountNumber=" + accountNumber + ", routingNumber="
				+ routingNumber + ", expiryDate=" + expiryDate + ", openDate=" + openDate + ", transactions="
				+ transactions + "]";
	}

}
