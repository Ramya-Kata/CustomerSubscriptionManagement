package com.ramyakata.microservice.entity;

/**
 * Enum representing the current status of a user account.
 * <p>
 * This is typically used to enable or restrict access based on account
 * condition.
 * 
 * Possible values:
 * <ul>
 * <li><strong>active</strong> - The user account is active and allowed to
 * authenticate.</li>
 * <li><strong>inactive</strong> - The user account exists but is currently
 * disabled or suspended.</li>
 * <li><strong>locked</strong> - The account is locked, possibly due to failed
 * login attempts or security policy.</li>
 * </ul>
 * 
 * This enum can be used in the {@code Users} entity or service logic to enforce
 * business rules based on user state.
 * 
 * @author Ramya Kata
 */
public enum Status {

	active, inactive, locked
}
