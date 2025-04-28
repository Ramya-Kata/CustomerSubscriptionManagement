package com.ramyakata.microservice.dao;

import org.springframework.data.domain.Page;

import com.ramyakata.microservice.entity.TvPlan;
import com.ramyakata.microservice.exception.SubscriptionException;

/**
 * Description: Service interface for managing TvPlan entities. Defines the
 * contract for adding, updating, deleting, and retrieving TV plans.
 */
public interface TvService {

	/**
	 * Description: Adds or updates a TV plan in the system.
	 * 
	 * @param plan The TvPlan object to add or update.
	 * @return The saved or updated TvPlan object.
	 * @throws SubscriptionException If the plan is null or there is an error during
	 *                               the operation.
	 */
	public TvPlan addOrUpdateTvPlan(TvPlan plan) throws SubscriptionException;

	/**
	 * Description: Deletes a TV plan from the system by ID.
	 * 
	 * @param id The unique ID of the TV plan to delete.
	 * @return True if the TV plan was successfully deleted, false otherwise.
	 * @throws SubscriptionException If the ID is null or the plan is not found.
	 */
	public Boolean deleteTvPlan(Long id) throws SubscriptionException;

	/**
	 * Description: Retrieves a paginated list of all TV plans.
	 * 
	 * @param pageNumber The page number to retrieve.
	 * @param pageSize   The number of plans to display per page.
	 * @param sortBy     The field to sort the plans by.
	 * @param direction  The direction to sort (asc/desc).
	 * @return A paginated list of TvPlan objects.
	 * @throws SubscriptionException If an error occurs during the retrieval
	 *                               process.
	 */
	public Page<TvPlan> getAllPlans(int pageNumber, int pageSize, String sortBy, String direction)
			throws SubscriptionException;

	/**
	 * Description: Retrieves a Tv plan.
	 * 
	 * @param id id of the plan
	 * @return plan details
	 * @throws SubscriptionException If an error occurs during the retrieval
	 *                               process.
	 */
	public TvPlan getTvPlanById(Long id) throws SubscriptionException;
}
