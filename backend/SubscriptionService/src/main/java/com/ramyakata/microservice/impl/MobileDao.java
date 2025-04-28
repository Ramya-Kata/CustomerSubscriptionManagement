package com.ramyakata.microservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ramyakata.microservice.dao.MobileService;
import com.ramyakata.microservice.entity.MobilePlan;
import com.ramyakata.microservice.exception.SubscriptionException;
import com.ramyakata.microservice.repo.MobilePlanRepo;

/**
 * Description: Service implementation for managing MobilePlan entities.
 * Provides business logic for adding, updating, deleting, and retrieving mobile
 * plans.
 */
@Service
public class MobileDao implements MobileService {

	@Autowired
	private MobilePlanRepo mobileRepo;

	/**
	 * Description: Adds or updates a mobile plan in the database.
	 * 
	 * @param plan The MobilePlan object to add or update.
	 * @return The saved MobilePlan object.
	 * @throws SubscriptionException If the plan is null or there is an error during
	 *                               the operation.
	 */
	@Override
	@CacheEvict(value = "mobilePlans", allEntries = true)
	public MobilePlan addOrUpdateMobilePlan(MobilePlan plan) throws SubscriptionException {

		try {
			if (plan == null) {
				throw new SubscriptionException("Mobile plan cannot be null");
			}

			if (plan.getMonthlyCost() == null || Double.isInfinite(plan.getMonthlyCost())
					|| Double.isNaN(plan.getMonthlyCost())) {
				throw new SubscriptionException("Invalid monthly cost value");
			}
			System.out.println("Plan ID: " + plan.getmobileId());
			// Check if ID is null, indicating it's a new plan to be created
			if (plan.getmobileId() == null) {
				return mobileRepo.save(plan); // Save new plan if ID is null
			} else {

				return mobileRepo.save(plan);
			}
		} catch (IllegalArgumentException ex) {
			throw new SubscriptionException("Invalid input: " + ex.getMessage());
		} catch (Exception ex) {
			throw new SubscriptionException("An unexpected error occurred while saving the plan: " + ex.getMessage());
		}
	}

	/**
	 * Description: Deletes a mobile plan from the database by ID.
	 * 
	 * @param id The ID of the mobile plan to delete.
	 * @return True if the plan is deleted successfully.
	 * @throws SubscriptionException If the ID is null or the plan is not found.
	 */
	@Override
	@CacheEvict(value = "mobilePlans", allEntries = true)
	public Boolean deleteMobilePlan(Long id) throws SubscriptionException {

		try {
			if (id == null) {
				throw new SubscriptionException("Mobile plan ID cannot be null");
			}
			if (!mobileRepo.existsById(id)) {
				throw new SubscriptionException("Mobile plan with ID " + id + " not found");
			}
			mobileRepo.deleteById(id);
			return true;
		} catch (Exception ex) {
			throw new SubscriptionException("An error occurred while deleting the plan: " + ex.getMessage());
		}
	}

	/**
	 * Description: Retrieves a paginated list of all mobile plans.
	 * 
	 * @param pageNumber The page number to retrieve.
	 * @param pageSize   The number of records per page.
	 * @param sortBy     The field to sort the results by.
	 * @param direction  The sort direction (asc/desc).
	 * @return A paginated list of MobilePlan objects.
	 * @throws SubscriptionException If an error occurs during retrieval.
	 */
	@Override
	@Cacheable(value = "mobilePlans")
	public Page<MobilePlan> getAllPlans(int pageNumber, int pageSize, String sortBy, String direction)
			throws SubscriptionException {
		try {

			Sort sort = Sort.by(Sort.Order.by(sortBy));
			if ("desc".equalsIgnoreCase(direction)) {
				sort = Sort.by(Sort.Order.desc(sortBy));
			}

			Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

			return mobileRepo.findAll(pageable);
		} catch (Exception ex) {
			throw new SubscriptionException("An error occurred while retrieving the plans: " + ex.getMessage());
		}
	}

	/**
	 * Description: Retrieves a Mobile plan.
	 * 
	 * @param id id of the plan
	 * @return plan details
	 * @throws SubscriptionException If an error occurs during the retrieval
	 *                               process.
	 */
	@Override
	public MobilePlan getMobilePlanById(Long id) throws SubscriptionException {
		if (id == null) {
			throw new SubscriptionException("Id not provided");
		}
		return mobileRepo.findById(id)
				.orElseThrow(() -> new SubscriptionException("Mobile plan with ID " + id + " not found"));

	}

}
