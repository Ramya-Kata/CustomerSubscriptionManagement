package com.ramyakata.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramyakata.microservice.domain.CalculationInput;
import com.ramyakata.microservice.domain.CalculationInputMapper;
import com.ramyakata.microservice.domain.CalculationOutput;
import com.ramyakata.microservice.dto.PlanDetails;
import com.ramyakata.microservice.exception.CalculationException;
import com.ramyakata.microservice.repo.PlanServiceProxy;
import com.ramyakata.microservice.svc.ICalculation;

import feign.FeignException;

/**
 * Description: REST controller for calculation-related operations.
 */
@RestController
@RequestMapping("/calculate/proxy")
public class CalculationProxyController {

	@Autowired
	private ICalculation calServiceObj;

	@Autowired
	private PlanServiceProxy planServiceProxy;

	/**
	 * Description: Endpoint to calculate payment details based on user inputs.
	 *
	 * @param planType The type of the plan (e.g., mobile, wifi, tv).
	 * @param planId   The ID of the plan to fetch details for.
	 * @return CalculationOutput with the total amount and other details.
	 * @throws CalculationException
	 */
	@GetMapping("/amount")
	public ResponseEntity<CalculationOutput> calculatePayment(@RequestParam("planType") String planType,
			@RequestParam("planId") Long planId, @RequestParam("numberOfMonths") String numberOfMonths)
			throws CalculationException {

		try {
			// Step 1: Dynamically fetch plan details based on planType
			PlanDetails planDetails = fetchPlanDetails(planType, planId);

			// Step 2: Extract required values from PlanDetails
			double baseAmount = planDetails.getMonthlyCost();

			double discountPercentage = planDetails.getDiscount();

			System.out.println("Received PlanDetails: {}" + planDetails);
			// Step 3: Map extracted values to CalculationInput
			CalculationInput input = CalculationInputMapper.mapToCalculationInput(baseAmount, numberOfMonths,
					discountPercentage);

			// Step 4: Perform the calculation using the service
			CalculationOutput output = calServiceObj.calculate(input);

			// Step 5: Return the result as a ResponseEntity
			return ResponseEntity.ok(output);
		} catch (CalculationException ex) {
			// Specific error handling
			throw new CalculationException("Error during calculation: " + ex.getMessage());
		} catch (Exception ex) {
			// General error handling for unexpected issues
			throw new RuntimeException("An unexpected error occurred while processing the request", ex);
		}
	}

	/**
	 * Fetches plan details dynamically based on the plan type.
	 *
	 * @param planType The type of the plan (e.g., mobile, wifi, tv).
	 * @param planId   The ID of the plan to fetch details for.
	 * @return The details of the plan.
	 * @throws CalculationException if the plan type is invalid or no details are
	 *                              found.
	 */
	private PlanDetails fetchPlanDetails(String planType, Long planId) throws CalculationException {
		try {
			switch (planType.toLowerCase()) {
			case "mobile":
				return planServiceProxy.getMobilePlan(planId);
			case "tv":
				return planServiceProxy.getTvPlan(planId);
			case "wifi":
				return planServiceProxy.getWifiPlan(planId);
			default:
				throw new CalculationException("Invalid plan type: " + planType);
			}
		} catch (FeignException e) {
			// Log the error for debugging
			System.err.println("Error fetching plan details: " + e.getMessage());
			throw new CalculationException("Error fetching plan details: " + e.getMessage());
		}
	}

}
