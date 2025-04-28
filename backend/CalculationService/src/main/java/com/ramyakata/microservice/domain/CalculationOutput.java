package com.ramyakata.microservice.domain;

/**
 * Description: Represents the output data from a payment calculation.
 */
public class CalculationOutput {

	/**
	 * Description: The base amount for a single month.
	 */
	private double baseAmount;

	/**
	 * Description: The number of months for which the calculation was performed.
	 */
	private String numberOfMonths;

	/**
	 * Description: The discount percentage that was applied.
	 */
	private double discountPercentage;
	
	private double tax;

	/**
	 * Description: The final total amount after applying discounts.
	 */
	private double totalAmount;

	

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	/**
	 * Description: Gets the base amount for a single month.
	 *
	 * @return the base amount
	 */
	public double getBaseAmount() {
		return baseAmount;
	}

	/**
	 * Description: Sets the base amount for a single month.
	 *
	 * @param baseAmount the base amount
	 */
	public void setBaseAmount(double baseAmount) {
		this.baseAmount = baseAmount;
	}

	/**
	 * Description: Gets the number of months for which the calculation was
	 * performed.
	 *
	 * @return the number of months
	 */
	public String getNumberOfMonths() {
		return numberOfMonths;
	}

	/**
	 * Description: Sets the number of months for which the calculation was
	 * performed.
	 *
	 * @param numberOfMonths the number of months
	 */
	public void setNumberOfMonths(String numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
	}

	/**
	 * Description: Gets the discount percentage that was applied.
	 *
	 * @return the discount percentage
	 */
	public double getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * Description: Sets the discount percentage that was applied.
	 *
	 * @param discountPercentage the discount percentage
	 */
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	/**
	 * Description: Gets the total amount after applying discounts.
	 *
	 * @return the total amount
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Description: Sets the total amount after applying discounts.
	 *
	 * @param totalAmount the total amount
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}