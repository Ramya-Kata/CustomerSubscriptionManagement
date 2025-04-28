package com.ramyakata.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramyakata.microservice.dao.TvService;
import com.ramyakata.microservice.entity.TvPlan;
import com.ramyakata.microservice.exception.SubscriptionException;

@RestController
@RequestMapping("/plan/tv")
public class TvController {

	@Autowired
	private TvService tvService;

	/**
	 * Description: Adds or updates a TV plan.
	 * 
	 * @param plan The TV plan to add or update.
	 * @return The saved or updated TV plan as a ResponseEntity.
	 * @throws SubscriptionException
	 */
	@PostMapping("/update")
	public ResponseEntity<TvPlan> addOrUpdateTvPlan(@RequestBody TvPlan plan) throws SubscriptionException {
		TvPlan updatedPlan = tvService.addOrUpdateTvPlan(plan);
		return ResponseEntity.ok(updatedPlan);
	}

	/**
	 * Description: Deletes a TV plan by its ID.
	 * 
	 * @param id The ID of the TV plan to delete.
	 * @return True if the plan was deleted successfully as a ResponseEntity.
	 * @throws SubscriptionException
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteTvPlan(@PathVariable Long id) throws SubscriptionException {
		Boolean isDeleted = tvService.deleteTvPlan(id);
		return ResponseEntity.ok(isDeleted);
	}

	/**
	 * Description: Retrieves a paginated list of all TV plans.
	 * 
	 * @param pageNumber The page number to retrieve.
	 * @param pageSize   The number of records per page.
	 * @param sortBy     The field to sort by.
	 * @param direction  The sort direction (ASC/DESC).
	 * @return A paginated list of TV plans as a ResponseEntity.
	 * @throws SubscriptionException
	 */
	@GetMapping("/get/all")
	public ResponseEntity<Page<TvPlan>> getAllTvPlans(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "tvId") String sortBy,
			@RequestParam(defaultValue = "ASC") String direction) throws SubscriptionException {
		Page<TvPlan> plans = tvService.getAllPlans(pageNumber, pageSize, sortBy, direction);
		return ResponseEntity.ok(plans);
	}

	/**
	 * Description: Retrieves the details of a specific Tv plan by its ID.
	 *
	 * @param id The ID of the Tv plan to retrieve.
	 * @return The TvPlan with the specified ID as a ResponseEntity.
	 * @throws SubscriptionException if the plan is not found or there is a
	 *                               retrieval issue.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<TvPlan> getTvPlanById(@PathVariable Long id) throws SubscriptionException {
		TvPlan plan = tvService.getTvPlanById(id);
		return ResponseEntity.ok(plan);
	}
}
