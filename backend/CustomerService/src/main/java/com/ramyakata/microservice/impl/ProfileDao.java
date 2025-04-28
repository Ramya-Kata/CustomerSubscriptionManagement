package com.ramyakata.microservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ramyakata.microservice.dao.ProfileService;
import com.ramyakata.microservice.entity.Address;
import com.ramyakata.microservice.entity.Customer;
import com.ramyakata.microservice.exception.CustomerException;
import com.ramyakata.microservice.repo.AddressRepo;
import com.ramyakata.microservice.repo.CustomerRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

/**
 * Implementation of the ProfileService interface. Provides business logic for
 * creating, updating, retrieving, and deleting customer profiles.
 */
@Service
public class ProfileDao implements ProfileService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AddressRepo addressRepo;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Description: Creates or updates a customer profile in the database.
	 *
	 * @param gmail    The email of the customer.
	 * @param customer The customer details to save or update.
	 * @param address  The address details to save or update.
	 * @return The updated or newly created customer entity.
	 * @throws CustomerException If the customer or address details are missing, or
	 *                           an unexpected error occurs.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, timeout = 60, rollbackFor = CustomerException.class)
	@CacheEvict(value = "customers", key = "#gmail")
	public Customer saveOrUpdateProfile(String gmail, Customer customer, Address address) throws CustomerException {

		if (address == null) {
			throw new CustomerException("Address details are missing.");
		}
		if (customer == null) {
			throw new CustomerException("Customer details are missing.");
		}

		Customer existingCustomer = null;
		try {
			existingCustomer = findProfile(gmail);
		} catch (CustomerException e) {

			existingCustomer = null;
		}
		try {
			if (existingCustomer != null) {
				existingCustomer.setFirstName(customer.getFirstName());
				existingCustomer.setLastName(customer.getLastName());
				existingCustomer.setGmail(gmail);
				existingCustomer.setPhoneNumber(customer.getPhoneNumber());
				existingCustomer.setDateOfBirth(customer.getDateOfBirth());
				addressRepo.save(address);
				existingCustomer.setAddrId(address);

				return customerRepo.save(existingCustomer);
			} else {
				addressRepo.save(address);
				customer.setGmail(gmail);
				customer.setAddrId(address);
				return customerRepo.save(customer);
			}
		} catch (Exception e) {
			throw new CustomerException(
					"An unexpected error occurred while saving or updating the profile." + e.getMessage());
		}

	}

	/**
	 * Description: Retrieves a customer profile by email.
	 *
	 * @param gmail The email of the customer to retrieve.
	 * @return The customer entity associated with the given email.
	 * @throws CustomerException If the email is null or the customer does not
	 *                           exist.
	 */
	@Cacheable(value = "customers", key = "#gmail")
	@Override
	public Customer findProfile(String gmail) throws CustomerException {

		if (gmail == null) {
			throw new CustomerException("Gmail is required to fetch customer details.");
		}

		try {
			Customer currentCustomer = customerRepo.getByGmail(gmail);
			if (currentCustomer == null) {
				throw new CustomerException("Customer not found with email: " + gmail);
			}
			return currentCustomer;
		} catch (EntityNotFoundException e) {
			throw new CustomerException("Customer not found: " + e.getMessage());
		}

	}

	/**
	 * Description: Deletes a customer profile by email.
	 *
	 * @param gmail The email of the customer to delete.
	 * @return True if the profile was successfully deleted; false otherwise.
	 * @throws CustomerException If the email is null, the customer does not exist,
	 *                           or a data integrity issue occurs.
	 */
	@CacheEvict(value = "customers", key = "#gmail")
	@Transactional(isolation = Isolation.READ_COMMITTED, timeout = 60, rollbackFor = CustomerException.class)
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Override
	public Boolean deleteProfile(String gmail) throws CustomerException {

		System.out.println("Entered into delete profile" + gmail);
		if (gmail == null) {
			throw new CustomerException("Gmail is required to delete the profile.");
		}

		Customer existingCustomer = null;
		try {
			existingCustomer = customerRepo.getByGmail(gmail);
			System.out.println("Esxisting Customer" + existingCustomer);
			if (existingCustomer == null) {
				throw new CustomerException("Customer not found with email: " + gmail);
			}
			customerRepo.delete(existingCustomer);
			//addressRepo.deleteById(existingCustomer.getId());
//			entityManager.flush();
//			// addressRepo.delete(existingCustomer.getAddrId());
//			System.out.println("After deleting address of Customer" + existingCustomer);
//			customerRepo.deleteById(existingCustomer.getId());
//			// customerRepo.deletByGmail(gmail);
//			entityManager.flush();
//			entityManager.clear();
			System.out.println("After deleting Customer" + existingCustomer);
////			customerRepo.deleteById(existingCustomer.getId());

		} catch (DataAccessException e) {
			throw new CustomerException("Database error" + e.getMessage()); // Properly wrapped
		} catch (Exception e) {
			throw new CustomerException("Couldn't delete profile due to an unknown error" + e.getMessage());
		}
		System.out.println("After deleting Customer" + existingCustomer);
		return true;

	}
	
	
//	@Override
//	public ProfileDTO findAll() throws CustomerException {
//		
//		
//		return null;
//	
//	}

}
