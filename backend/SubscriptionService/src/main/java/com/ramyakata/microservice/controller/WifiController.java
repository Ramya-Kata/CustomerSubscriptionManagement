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

import com.ramyakata.microservice.dao.WifiService;
import com.ramyakata.microservice.entity.WifiPlan;
import com.ramyakata.microservice.exception.SubscriptionException;

@RestController
@RequestMapping("/plan/wifi")
public class WifiController {

	@Autowired
	private WifiService wifiService;

	/**
	 * Description: Adds or updates a WiFi plan.
	 * 
	 * @param plan The WiFi plan to add or update.
	 * @return The saved or updated WiFi plan as a ResponseEntity.
	 * @throws SubscriptionException
	 */
	@PostMapping("/update")
	public ResponseEntity<WifiPlan> addOrUpdateWifiPlan(@RequestBody WifiPlan plan) throws SubscriptionException {
		WifiPlan updatedPlan = wifiService.addOrUpdateWifiPlan(plan);
		return ResponseEntity.ok(updatedPlan);
	}

	/**
	 * Description: Deletes a WiFi plan by its ID.
	 * 
	 * @param id The ID of the WiFi plan to delete.
	 * @return True if the plan was deleted successfully as a ResponseEntity.
	 * @throws SubscriptionException
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteWifiPlan(@PathVariable Long id) throws SubscriptionException {
		Boolean isDeleted = wifiService.deleteWifiPlan(id);
		return ResponseEntity.ok(isDeleted);
	}

	/**
	 * Description: Retrieves a paginated list of all WiFi plans.
	 * 
	 * @param pageNumber The page number to retrieve.
	 * @param pageSize   The number of records per page.
	 * @param sortBy     The field to sort by.
	 * @param direction  The sort direction (ASC/DESC).
	 * @return A paginated list of WiFi plans as a ResponseEntity.
	 * @throws SubscriptionException
	 */
	@GetMapping("/get/all")
	public ResponseEntity<Page<WifiPlan>> getAllWifiPlans(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "ASC") String direction) throws SubscriptionException {
		Page<WifiPlan> plans = wifiService.getAllPlans(pageNumber, pageSize, sortBy, direction);
		return ResponseEntity.ok(plans);
	}

	/**
	 * Description: Retrieves the details of a specific wifi plan by its ID.
	 *
	 * @param id The ID of the wifi plan to retrieve.
	 * @return The wifiPlan with the specified ID as a ResponseEntity.
	 * @throws SubscriptionException if the plan is not found or there is a
	 *                               retrieval issue.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<WifiPlan> getWifiPlanById(@PathVariable Long id) throws SubscriptionException {
		WifiPlan plan = wifiService.getWifiPlanById(id);
		return ResponseEntity.ok(plan);
	}
}
