package com.ramyakata.microservice.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity representing the address details of a customer.
 */

@Entity
@Table(name = "Customer_Address")
public class Address {

	/**
	 * Description: The unique identifier for the address.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Addr_Id")
	private Long id;

	/**
	 * Description: The street information of the address.
	 */
	@Column(name = "Street")
	private String street;

	/**
	 * Description: The apartment number.
	 */
	@Column(name = "Apt_Num")
	private String apt;

	/**
	 * Description: The county information.
	 */
	@Column(name = "County")
	private String county;

	/**
	 * Description: The state information.
	 */
	@Column(name = "State")
	private String state;

	/**
	 * Description: The country information.
	 */
	@Column(name = "Country")
	private String country;

	/**
	 * Description: The postal code.
	 */
	@Column(name = "Postal_Code")
	private String postalCode;

	public Address() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getApt() {
		return apt;
	}

	public void setApt(String apt) {
		this.apt = apt;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", apt=" + apt + ", county=" + county + ", state=" + state
				+ ", country=" + country + ", postalCode=" + postalCode + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(id, other.id);
	}

}
