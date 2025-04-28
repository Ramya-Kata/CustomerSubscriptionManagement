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
 * Description: Represents a TV subscription plan with attributes such as
 * premium channels, monthly cost, and discount percentage. Each TV plan has a
 * unique ID.
 */
@Entity
@Table(name = "TV_Plan")
public class TvPlan {

	/**
	 * Description: Unique identifier for the TV plan.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tv_sequence")
	@SequenceGenerator(name = "tv_sequence", sequenceName = "tv_id_sequence", initialValue = 9990001, allocationSize = 1)
	@Column(name = "Tv_Id")
	private Long tvId;

	/**
	 * Description: Name of the TV plan.
	 */
	@Column(name = "Plan_Name")
	private String planName;

	/**
	 * Description: Number of premium channels included in the plan.
	 */
	@Column(name = "premium_channels")
	private Integer premiumChannels;

	/**
	 * Description: Monthly cost of the TV plan.
	 */
	@Column(name = "Monthly_Cost")
	private Double monthlyCost;

	/**
	 * Description: Discount percentage applicable to the TV plan.
	 */
	@Column(name = "Discount_Percentage")
	private Double discount;

	public Long getTvId() {
		return tvId;
	}

	public void setTvId(Long tvId) {
		this.tvId = tvId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getPremiumChannels() {
		return premiumChannels;
	}

	public void setPremiumChannels(Integer premiumChannels) {
		this.premiumChannels = premiumChannels;
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
		return "TvPlan [tvId=" + tvId + ", planName=" + planName + ", premium_Channels=" + premiumChannels
				+ ", monthlyCost=" + monthlyCost + ", discount=" + discount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(discount, monthlyCost, planName, premiumChannels, tvId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TvPlan other = (TvPlan) obj;
		return Objects.equals(discount, other.discount) && Objects.equals(monthlyCost, other.monthlyCost)
				&& Objects.equals(planName, other.planName) && Objects.equals(premiumChannels, other.premiumChannels)
				&& Objects.equals(tvId, other.tvId);
	}

}
