package com.ramyakata.microservice.entity;

/**
 *Description: Entity class representing a bank account type in the system.
 * The enum defines two types of bank accounts:
 * <ul>
 *   <li>{@link #CREDIT} - A credit account type.</li>
 *   <li>{@link #DEBIT} - A debit account type.</li>
 * </ul>
 * This enum is used in the {@link Account} entity to specify the type of the bank account.
 * 
 *
 */
public enum AccountType {

	/**
     *Description: Represents a credit account type, typically used for accounts where a credit limit is assigned.
     */
	CREDIT,
	/**
     *Description: Represents a debit account type, typically used for accounts linked to available funds.
     */
	DEBIT;
	
	
	public static AccountType caseType(String accountTypeStr) {
        if (accountTypeStr != null) {
            for (AccountType accountType : AccountType.values()) {
                if (accountType.name().equalsIgnoreCase(accountTypeStr)) {
                    return accountType;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant with " + accountTypeStr + " found");
    }
}
