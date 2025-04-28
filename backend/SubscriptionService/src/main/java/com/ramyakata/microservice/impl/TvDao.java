package com.ramyakata.microservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ramyakata.microservice.dao.TvService;
import com.ramyakata.microservice.entity.TvPlan;
import com.ramyakata.microservice.exception.SubscriptionException;
import com.ramyakata.microservice.repo.TvPlanRepo;

/**
 * Description: Service implementation for managing TvPlan entities. Provides
 * business logic for adding, updating, deleting, and retrieving TV plans.
 */
@Service
public class TvDao implements TvService {

	@Autowired
	private TvPlanRepo tvRepo;

	/**
	 * Description: Adds or updates a TV plan in the database.
	 * 
	 * @param plan The TvPlan object to add or update.
	 * @return The saved TvPlan object.
	 * @throws SubscriptionException If the plan is null or there is an error during
	 *                               the operation.
	 */
	@Override
	@CacheEvict(value = "tvPlans", allEntries = true)
	public TvPlan addOrUpdateTvPlan(TvPlan plan) throws SubscriptionException {

		try {
			if (plan == null || plan.getPlanName() == null) {
				throw new SubscriptionException("Tv plan cannot be null");
			}
			if (plan.getTvId() == null) {
				return tvRepo.save(plan); // Save new plan if ID is null
			} else {

				return tvRepo.save(plan);
			}
		} catch (IllegalArgumentException ex) {
			throw new SubscriptionException("Invalid input: " + ex.getMessage());
		} catch (Exception ex) {
			throw new SubscriptionException("An unexpected error occurred while saving the plan: " + ex.getMessage());
		}
	}

	/**
	 * Description: Deletes a TV plan from the database by ID.
	 * 
	 * @param id The ID of the TV plan to delete.
	 * @return True if the plan is deleted successfully.
	 * @throws SubscriptionException If the ID is null or the plan is not found.
	 */
	@Override
	@CacheEvict(value = "tvPlans", allEntries = true)
	public Boolean deleteTvPlan(Long id) throws SubscriptionException {

		try {
			if (id == null) {
				throw new SubscriptionException("Tv plan ID cannot be null");
			}
			if (!tvRepo.existsById(id)) {
				throw new SubscriptionException("Tv plan with ID " + id + " not found");
			}
			tvRepo.deleteById(id);
			return true;
		} catch (Exception ex) {
			throw new SubscriptionException("An error occurred while deleting the plan: " + ex.getMessage());
		}
	}

	/**
	 * Description: Retrieves a paginated list of all TV plans.
	 * 
	 * @param pageNumber The page number to retrieve.
	 * @param pageSize   The number of records per page.
	 * @param sortBy     The field to sort the results by.
	 * @param direction  The sort direction (asc/desc).
	 * @return A paginated list of TvPlan objects.
	 * @throws SubscriptionException If an error occurs during retrieval.
	 */
	@Override
	@Cacheable(value = "tvPlans")
	public Page<TvPlan> getAllPlans(int pageNumber, int pageSize, String sortBy, String direction)
			throws SubscriptionException {

		try {

			Sort sort = Sort.by(Sort.Order.by(sortBy));
			if ("desc".equalsIgnoreCase(direction)) {
				sort = Sort.by(Sort.Order.desc(sortBy));
			}

			Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

			return tvRepo.findAll(pageable);
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
	public TvPlan getTvPlanById(Long id) throws SubscriptionException {
		if (id == null) {
			throw new SubscriptionException("Id not provided");
		}
		return tvRepo.findById(id).orElseThrow(() -> new SubscriptionException("Tv plan with ID " + id + " not found"));

	}

}
