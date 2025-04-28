package com.ramyakata.microservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ramyakata.microservice.dao.WifiService;
import com.ramyakata.microservice.entity.WifiPlan;
import com.ramyakata.microservice.exception.SubscriptionException;
import com.ramyakata.microservice.repo.WifiPlanRepo;

@SpringBootTest
class WifiServiceTest {

	@Autowired
	private WifiService wifiService;

	@Autowired
	private WifiPlanRepo wifiPlanRepo;

	private WifiPlan validPlan;
	private WifiPlan invalidPlan;

	@BeforeEach
	void setUp() {
		wifiPlanRepo.deleteAll();

		validPlan = new WifiPlan();
		validPlan.setPlanName("Basic Wifi Plan");
		validPlan.setDataLimit("50GB");
		validPlan.setSpeed(100);
		validPlan.setMonthlyCost(15.0);
		validPlan.setDiscount(10.0);

		invalidPlan = new WifiPlan();
		invalidPlan.setPlanName(null); // Invalid due to null name
	}

	@Test
	void testAddWifiPlan_ValidPlan() throws SubscriptionException {
		WifiPlan savedPlan = wifiService.addOrUpdateWifiPlan(validPlan);

		assertNotNull(savedPlan.getId());
		assertEquals("Basic Wifi Plan", savedPlan.getPlanName());
	}

	@Test
	void testAddWifiPlan_InvalidPlan() {
		Exception exception = assertThrows(SubscriptionException.class, () -> {
			wifiService.addOrUpdateWifiPlan(invalidPlan);
		});

		assertTrue(exception.getMessage().contains("Wifi plan cannot be null"));
	}

	@Test
	void testUpdateWifiPlan() throws SubscriptionException {
		WifiPlan savedPlan = wifiService.addOrUpdateWifiPlan(validPlan);

		savedPlan.setPlanName("Updated Wifi Plan");
		WifiPlan updatedPlan = wifiService.addOrUpdateWifiPlan(savedPlan);

		assertEquals("Updated Wifi Plan", updatedPlan.getPlanName());
	}

	@Test
	void testDeleteWifiPlan_ValidId() throws SubscriptionException {
		WifiPlan savedPlan = wifiService.addOrUpdateWifiPlan(validPlan);

		assertTrue(wifiService.deleteWifiPlan(savedPlan.getId()));
	}

	@Test
	void testDeleteWifiPlan_InvalidId() {
		Exception exception = assertThrows(SubscriptionException.class, () -> {
			wifiService.deleteWifiPlan(9999L);
		});

		assertEquals("An error occurred while deleting the plan: Wifi plan with ID 9999 not found",
				exception.getMessage());
	}

	@Test
	void testDeleteWifiPlan_NullId() {
		Exception exception = assertThrows(SubscriptionException.class, () -> {
			wifiService.deleteWifiPlan(null);
		});

		assertEquals("An error occurred while deleting the plan: Wifi plan ID cannot be null", exception.getMessage());
	}

	@Test
	void testGetAllPlans_WithData() throws SubscriptionException {
		WifiPlan savedPlan1 = wifiService.addOrUpdateWifiPlan(validPlan);

		WifiPlan secondPlan = new WifiPlan();
		secondPlan.setPlanName("Premium Wifi Plan");
		secondPlan.setDataLimit("Unlimited");
		secondPlan.setSpeed(500);
		secondPlan.setMonthlyCost(30.0);
		secondPlan.setDiscount(15.0);
		WifiPlan savedPlan2 = wifiService.addOrUpdateWifiPlan(secondPlan);

		var plans = wifiService.getAllPlans(0, 10, "id", "ASC");

		assertEquals(2, plans.getContent().size());
		assertTrue(plans.getContent().get(0).getId() < plans.getContent().get(1).getId());
		assertEquals(savedPlan1, plans.getContent().get(0));
		assertEquals(savedPlan2, plans.getContent().get(1));
	}

	@Test
	void testGetAllPlans_InvalidPageParameters() {
		Exception exception = assertThrows(Exception.class, () -> {
			wifiService.getAllPlans(-1, -10, "id", "ASC");
		});

		assertTrue(exception.getMessage().contains("Page index must not be less than zero"));
	}
}