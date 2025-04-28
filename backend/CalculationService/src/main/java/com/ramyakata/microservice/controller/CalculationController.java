package com.ramyakata.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramyakata.microservice.domain.CalculationInputMapper;
import com.ramyakata.microservice.domain.CalculationOutput;
import com.ramyakata.microservice.exception.CalculationException;
import com.ramyakata.microservice.svc.ICalculation;

/**
 * Description: REST controller for calculation-related operations.
 */
@RestController
@RequestMapping("/calculate/normal")
public class CalculationController {

	@Autowired
	private ICalculation calServiceObj;

	/**
	 * Description: Endpoint to calculate payment details based on user inputs.
	 *
	 * @param baseAmount         the base amount for the calculation
	 * @param numberOfMonths     the subscription duration in months
	 * @param discountPercentage the discount percentage to apply
	 * @return CalculationOutput with the total amount and other details
	 * @throws CalculationException if input validation fails
	 */
	@GetMapping("/amount")
	public CalculationOutput calculatePayment(@RequestParam("baseAmount") double baseAmount,
			@RequestParam("numberOfMonths") String numberOfMonths,
			@RequestParam("discountPercentage") double discountPercentage) throws CalculationException {

		return calServiceObj.calculate(
				CalculationInputMapper.mapToCalculationInput(baseAmount, numberOfMonths, discountPercentage));
	}

}
