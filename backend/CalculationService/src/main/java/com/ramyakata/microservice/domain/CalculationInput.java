package com.ramyakata.microservice.domain;

/**
 * Description: Represents the input data required for a payment calculation.
 */
public class CalculationInput {

	/**
     * Description: The base amount for a single month.
     */
    private double baseAmount;

    /**
     * Description: The number of months for which the calculation is to be performed.
     */
    private String numberOfMonths;

    /**
     * Description: The discount percentage to be applied to the total amount.
     */
    private double discountPercentage;

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
     * Description: Gets the number of months for the calculation.
     *
     * @return the number of months
     */
    public String getNumberOfMonths() {
        return numberOfMonths;
    }

    /**
     * Description: Sets the number of months for the calculation.
     *
     * @param numberOfMonths the number of months
     */
    public void setNumberOfMonths(String numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    /**
     * Description: Gets the discount percentage to be applied.
     *
     * @return the discount percentage
     */
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * Description: Sets the discount percentage to be applied.
     *
     * @param discountPercentage the discount percentage
     */
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}