package com.ramyakata.microservice.dto;

import com.ramyakata.microservice.entity.Address;
import com.ramyakata.microservice.entity.Customer;

/**
 * Data Transfer Object (DTO) for customer profile operations. Encapsulates
 * customer and address details for creating or updating profiles.
 */
public class ProfileDTO {
	/**
	 * Description: The customer details.
	 */
	private Customer customer;

	/**
	 * Description: The address details.
	 */
	private Address address;

	/**
	 * Description: Retrieves the customer details.
	 *
	 * @return The customer entity.
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Description: Sets the customer details.
	 *
	 * @param customer The customer entity to set.
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Description: Retrieves the address details.
	 *
	 * @return The address entity.
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Description: Sets the address details.
	 *
	 * @param address The address entity to set.
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

}
