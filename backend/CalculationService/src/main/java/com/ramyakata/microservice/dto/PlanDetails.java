package com.ramyakata.microservice.dto;

/**
 * Data Transfer Object (DTO) representing the details of a subscription plan.
 * <p>
 * This class is used by the Calculation Service to fetch plan data from remote
 * services (via Feign) and extract pricing components needed for cost
 * computation.
 * <p>
 * Fields:
 * <ul>
 * <li><strong>id</strong> – Unique plan identifier</li>
 * <li><strong>planName</strong> – Name of the plan (e.g., Gold, Basic)</li>
 * <li><strong>dataLimit</strong> – Data limit for the plan (e.g., 100GB)</li>
 * <li><strong>speed</strong> – Connection speed in Mbps</li>
 * <li><strong>monthlyCost</strong> – Monthly subscription fee</li>
 * <li><strong>discount</strong> – Discount percentage applicable</li>
 * </ul>
 * 
 * This object is used by
 * {@link com.ramyakata.microservice.controller.CalculationProxyController}
 * during dynamic cost calculations.
 * 
 * Author: Ramya Kata
 */
public class PlanDetails {

	private Long id;
	private String planName;
	private String dataLimit;
	private Integer speed;
	private Double monthlyCost;
	private Double discount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getDataLimit() {
		return dataLimit;
	}

	public void setDataLimit(String dataLimit) {
		this.dataLimit = dataLimit;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Double getMonthlyCost() {
		return monthlyCost;
	}

	public void setMonthlyCost(Double monthlyCost) {
		this.monthlyCost = monthlyCost;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

}