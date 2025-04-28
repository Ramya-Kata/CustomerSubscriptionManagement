package com.ramyakata.microservice.entity;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entity representing the customer details.
 */
@Entity
@Table(name = "CUSTOMER_DETAILS")
public class Customer {

	/**
	 * Description: The unique identifier for the customer.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Customer_Id")
	private Long id;

	/**
	 * Description: The first name of the customer.
	 */
	@Column(name = "First_Name", nullable = false)
	private String firstName;

	/**
	 * Description: The last name of the customer.
	 */
	@Column(name = "Last_Name", nullable = false)
	private String lastName;

	/**
	 * Description: The unique email (Gmail) of the customer.
	 */
	@Column(name = "Gmail", unique = true, nullable = false)
	private String gmail;

	/**
	 * Description: The date of birth of the customer.
	 */
	@Column(name = "Date_Of_Birth")
	private Date dateOfBirth;

	/**
	 * Description: The phone number of the customer.
	 */
	@Column(name = "Phone_Number")
	private String phoneNumber;

	/**
	 * Description: The address associated with the customer.
	 */
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "Addr_Id", referencedColumnName = "Addr_Id")
	private Address addrId;

	public Customer() {

	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Address getAddrId() {
		return addrId;
	}

	public void setAddrId(Address addrId) {
		this.addrId = addrId;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gmail=" + gmail
				+ ", dateOfBirth=" + dateOfBirth + ", phoneNumber=" + phoneNumber + ", addrId=" + addrId + "]";
	}

	/**
	 * Description: Generates hash code based on the unique Gmail field.
	 *
	 * @return The hash code of the Gmail field.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(gmail);
	}

	/**
	 * Description: Checks equality based on the unique Gmail field.
	 *
	 * @param o The object to compare.
	 * @return True if the objects are equal; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(gmail, other.gmail);
	}

}
