package com.ramyakata.microservice.dao;

import org.springframework.data.domain.Page;

import com.ramyakata.microservice.entity.MobilePlan;
import com.ramyakata.microservice.exception.SubscriptionException;

/**
 * Description: Service interface for managing MobilePlan entities. Defines the
 * contract for adding, updating, deleting, and retrieving mobile plans.
 */
public interface MobileService {

	/**
	 * Description: Adds or updates a mobile plan in the system.
	 * 
	 * @param plan The MobilePlan object to add or update.
	 * @return The saved or updated MobilePlan object.
	 * @throws SubscriptionException If the plan is null or there is an error during
	 *                               the operation.
	 */
	public MobilePlan addOrUpdateMobilePlan(MobilePlan plan) throws SubscriptionException;

	/**
	 * Description: Deletes a mobile plan from the system by ID.
	 * 
	 * @param id The unique ID of the mobile plan to delete.
	 * @return True if the mobile plan was successfully deleted, false otherwise.
	 * @throws SubscriptionException If the ID is null or the plan is not found.
	 */
	public Boolean deleteMobilePlan(Long id) throws SubscriptionException;

	/**
	 * Description: Retrieves a paginated list of all mobile plans.
	 * 
	 * @param pageNumber The page number to retrieve.
	 * @param pageSize   The number of plans to display per page.
	 * @param sortBy     The field to sort the plans by.
	 * @param direction  The direction to sort (asc/desc).
	 * @return A paginated list of MobilePlan objects.
	 * @throws SubscriptionException If an error occurs during the retrieval
	 *                               process.
	 */
	public Page<MobilePlan> getAllPlans(int pageNumber, int pageSize, String sortBy, String direction)
			throws SubscriptionException;

	/**
	 * Description: Retrieves a Mobile plan.
	 * 
	 * @param id id of the plan
	 * @return plan details
	 * @throws SubscriptionException If an error occurs during the retrieval
	 *                               process.
	 */
	public MobilePlan getMobilePlanById(Long id) throws SubscriptionException;
}
