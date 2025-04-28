package com.ramyakata.microservice.domain;

/**
 * Description: Class to map raw input to Input Object
 */
public class CalculationInputMapper {

	/**
	 * Description: Converts raw input parameters to a CalculationInput object.
	 *
	 * @param baseAmount         the base amount for the calculation
	 * @param numberOfMonths     the subscription duration
	 * @param discountPercentage the discount percentage
	 * @return a CalculationInput object
	 */
	public static CalculationInput mapToCalculationInput(double baseAmount, String numberOfMonths,
			double discountPercentage) {
		CalculationInput input = new CalculationInput();
		input.setBaseAmount(baseAmount);
		input.setNumberOfMonths(numberOfMonths);
		input.setDiscountPercentage(discountPercentage);
		return input;
	}
}
