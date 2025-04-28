package com.ramyakata.microservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ramyakata.microservice.dao.MobileService;
import com.ramyakata.microservice.entity.MobilePlan;
import com.ramyakata.microservice.exception.SubscriptionException;
import com.ramyakata.microservice.repo.MobilePlanRepo;

@SpringBootTest
class MobileServiceTest {

	@Autowired
	private MobileService mobileService;

	@Autowired
	private MobilePlanRepo mobilePlanRepo;
	private MobilePlan validPlan;
	private MobilePlan invalidPlan;

	@BeforeEach
	void setUp() {
		mobilePlanRepo.deleteAll();

		validPlan = new MobilePlan();
		validPlan.setPlanName("Basic Plan");
		validPlan.setDataLimit("5GB");
		validPlan.setCallMinutes(100);
		validPlan.setTextLimit(50);
		validPlan.setMonthlyCost(10.0);
		validPlan.setDiscount(5.0);

		invalidPlan = new MobilePlan();
		invalidPlan.setPlanName(null);
	}

	@Test
	void testAddMobilePlan_ValidPlan() throws SubscriptionException {
		MobilePlan savedPlan = mobileService.addOrUpdateMobilePlan(validPlan);

		assertNotNull(savedPlan.getmobileId());
		assertEquals("Basic Plan", savedPlan.getPlanName());
	}

	@Test
	void testAddMobilePlan_InvalidPlan() {
		Exception exception = assertThrows(SubscriptionException.class, () -> {
			mobileService.addOrUpdateMobilePlan(invalidPlan);
		});

		assertEquals("An unexpected error occurred while saving the plan: Invalid monthly cost value",
				exception.getMessage());
	}

	@Test
	void testUpdateMobilePlan() throws SubscriptionException {
		MobilePlan savedPlan = mobileService.addOrUpdateMobilePlan(validPlan);

		savedPlan.setPlanName("Updated Plan");
		MobilePlan updatedPlan = mobileService.addOrUpdateMobilePlan(savedPlan);

		assertEquals("Updated Plan", updatedPlan.getPlanName());
	}

	@Test
	void testDeleteMobilePlan_ValidId() throws SubscriptionException {
		MobilePlan savedPlan = mobileService.addOrUpdateMobilePlan(validPlan);

		assertTrue(mobileService.deleteMobilePlan(savedPlan.getmobileId()));
	}

	@Test
	void testDeleteMobilePlan_InvalidId() {
		Exception exception = assertThrows(SubscriptionException.class, () -> {
			mobileService.deleteMobilePlan(9999L);
		});

		assertEquals("An error occurred while deleting the plan: Mobile plan with ID 9999 not found",
				exception.getMessage());
	}

	@Test
	void testDeleteMobilePlan_NullId() {
		Exception exception = assertThrows(SubscriptionException.class, () -> {
			mobileService.deleteMobilePlan(null);
		});

		assertEquals("An error occurred while deleting the plan: Mobile plan ID cannot be null",
				exception.getMessage());
	}

	@Test
	void testGetAllPlans_WithData() throws SubscriptionException {
		// Step 1: Add the first mobile plan
		MobilePlan savedPlan1 = mobileService.addOrUpdateMobilePlan(validPlan);

		// Step 2: Add a second mobile plan
		MobilePlan secondPlan = new MobilePlan();
		secondPlan.setPlanName("Second Plan");
		secondPlan.setDataLimit("10GB");
		secondPlan.setCallMinutes(200);
		secondPlan.setTextLimit(100);
		secondPlan.setMonthlyCost(20.0);
		secondPlan.setDiscount(10.0);
		MobilePlan savedPlan2 = mobileService.addOrUpdateMobilePlan(secondPlan);

		// Step 3: Retrieve all plans with pagination and sorting
		var plans = mobileService.getAllPlans(0, 10, "mobileId", "ASC");

		// Step 4: Assert the size of the result
		assertEquals(2, plans.getContent().size(), "The number of plans returned should be 2.");

		// Step 5: Assert that the plans are sorted by mobileId in ascending order
		assertTrue(plans.getContent().get(0).getmobileId() < plans.getContent().get(1).getmobileId(),
				"The plans should be sorted by mobileId in ascending order.");

		// Step 6: Validate the contents of the retrieved plans
		assertEquals(savedPlan1, plans.getContent().get(0),
				"The first retrieved plan should match the saved first plan.");
		assertEquals(savedPlan2, plans.getContent().get(1),
				"The second retrieved plan should match the saved second plan.");
	}

	@Test
	void testGetAllPlans_InvalidPageParameters() {
		Exception exception = assertThrows(Exception.class, () -> {
			mobileService.getAllPlans(-1, -10, "mobileId", "ASC");
		});

		assertTrue(exception.getMessage().contains("Page index must not be less than zero"));
	}

	@Test
	void testAddOrUpdateMobilePlan_UnexpectedException() {

		Exception exception = assertThrows(Exception.class, () -> {
			MobilePlan planWithInvalidField = new MobilePlan();
			planWithInvalidField.setPlanName("Faulty Plan");
			planWithInvalidField.setMonthlyCost(Double.POSITIVE_INFINITY); 
			mobileService.addOrUpdateMobilePlan(planWithInvalidField);
		});

		assertTrue(exception.getMessage().contains("unexpected error occurred"));
	}
}