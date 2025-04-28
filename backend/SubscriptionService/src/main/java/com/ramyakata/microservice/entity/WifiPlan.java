package com.ramyakata.microservice.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Description: Represents a WiFi plan with attributes like data limit, speed,
 * monthly cost, and discount percentage. Each WiFi plan has a unique ID.
 */
@Entity
@Table(name = "Wifi_Plan")
public class WifiPlan {

	/**
	 * Description: Unique identifier for the WiFi plan.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wifi_sequence")
	@SequenceGenerator(name = "wifi_sequence", sequenceName = "wifi_id_sequence", initialValue = 4440001, allocationSize = 1)
	@Column(name = "Wifi_Id")
	private Long id;

	/**
	 * Description: Name of the WiFi plan.
	 */
	@Column(name = "Plan_Name")
	private String planName;

	/**
	 * Description: Data limit offered in the WiFi plan (e.g., "Unlimited",
	 * "500GB").
	 */
	@Column(name = "Data_Limit")
	private String dataLimit;

	/**
	 * Description: Speed offered in the WiFi plan (in Mbps).
	 */
	@Column(name = "Speed")
	private Integer speed;

	/**
	 * Description: Monthly cost of the WiFi plan.
	 */
	@Column(name = "Monthly_Cost")
	private Double monthlyCost;

	/**
	 * Description: Discount percentage applicable to the WiFi plan.
	 */
	@Column(name = "Discount_Percentage")
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

	@Override
	public String toString() {
		return "WifiPlan [id=" + id + ", planName=" + planName + ", dataLimit=" + dataLimit + ", speed=" + speed
				+ ", monthlyCost=" + monthlyCost + ", discount=" + discount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataLimit, discount, id, monthlyCost, planName, speed);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WifiPlan other = (WifiPlan) obj;
		return Objects.equals(dataLimit, other.dataLimit) && Objects.equals(discount, other.discount)
				&& Objects.equals(id, other.id) && Objects.equals(monthlyCost, other.monthlyCost)
				&& Objects.equals(planName, other.planName) && Objects.equals(speed, other.speed);
	}

}
