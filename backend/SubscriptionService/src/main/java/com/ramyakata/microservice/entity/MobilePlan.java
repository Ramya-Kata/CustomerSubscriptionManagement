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
 * Description: Represents a mobile plan with various attributes like data
 * limit, call minutes, text limit, monthly cost, and applicable discount
 * percentage. Each mobile plan has a unique ID.
 */

@Entity
@Table(name = "Mobile_Plan")
public class MobilePlan {

	/**
	 * Description: Unique identifier for the mobile plan.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mobile_sequence")
	@SequenceGenerator(name = "mobile_sequence", sequenceName = "mobile_id_sequence", initialValue = 8880001, allocationSize = 1)
	@Column(name = "Mobile_Id")
	private Long mobileId;

	/**
	 * Description: Name of the mobile plan.
	 */
	@Column(name = "Plan_Name")
	private String planName;

	/**
	 * Description: Data limit offered in the mobile plan (e.g., "10GB").
	 */
	@Column(name = "Data_Limit")
	private String dataLimit;

	/**
	 * Description: Number of call minutes included in the plan.
	 */
	@Column(name = "Call_Minutes")
	private Integer callMinutes;

	/**
	 * Description: Number of text messages allowed in the plan.
	 */
	@Column(name = "text_limit")
	private Integer textLimit;

	/**
	 * Description: Monthly cost of the mobile plan.
	 */
	@Column(name = "Monthly_Cost")
	private Double monthlyCost;

	/**
	 * Description: Discount percentage applicable to the mobile plan.
	 */
	@Column(name = "Discount_Percentage")
	private Double discount;

	public Long getmobileId() {
		return mobileId;
	}

	public void setmobileId(Long mobileId) {
		this.mobileId = mobileId;
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

	public Integer getCallMinutes() {
		return callMinutes;
	}

	public void setCallMinutes(Integer callMinutes) {
		this.callMinutes = callMinutes;
	}

	public Integer getTextLimit() {
		return textLimit;
	}

	public void setTextLimit(Integer textLimit) {
		this.textLimit = textLimit;
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
		return "MobilePlan [id=" + mobileId + ", planName=" + planName + ", dataLimit=" + dataLimit + ", callMinutes="
				+ callMinutes + ", text_limit=" + textLimit + ", monthlyCost=" + monthlyCost + ", discount=" + discount
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(callMinutes, dataLimit, discount, mobileId, monthlyCost, planName, textLimit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MobilePlan other = (MobilePlan) obj;
		return Objects.equals(callMinutes, other.callMinutes) && Objects.equals(dataLimit, other.dataLimit)
				&& Objects.equals(discount, other.discount) && Objects.equals(mobileId, other.mobileId)
				&& Objects.equals(monthlyCost, other.monthlyCost) && Objects.equals(planName, other.planName)
				&& Objects.equals(textLimit, other.textLimit);
	}

}
