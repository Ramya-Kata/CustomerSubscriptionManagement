package com.ramyakata.microservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ramyakata.microservice.dao.TvService;
import com.ramyakata.microservice.entity.TvPlan;
import com.ramyakata.microservice.exception.SubscriptionException;
import com.ramyakata.microservice.repo.TvPlanRepo;

@SpringBootTest
class TvServiceTest {

	@Autowired
	private TvService tvService;

	@Autowired
	private TvPlanRepo tvPlanRepo;

	private TvPlan validPlan;
	private TvPlan invalidPlan;

	@BeforeEach
	void setUp() {
		tvPlanRepo.deleteAll();

		validPlan = new TvPlan();
		validPlan.setPlanName("Basic TV Plan");
		validPlan.setPremiumChannels(10);
		validPlan.setMonthlyCost(20.0);
		validPlan.setDiscount(5.0);

		invalidPlan = new TvPlan();
		invalidPlan.setPlanName(null); // Invalid due to null name
	}

	@Test
	void testAddTvPlan_ValidPlan() throws SubscriptionException {
		TvPlan savedPlan = tvService.addOrUpdateTvPlan(validPlan);

		assertNotNull(savedPlan.getTvId());
		assertEquals("Basic TV Plan", savedPlan.getPlanName());
	}

	@Test
	void testAddTvPlan_InvalidPlan() {
		Exception exception = assertThrows(SubscriptionException.class, () -> {
			tvService.addOrUpdateTvPlan(invalidPlan);
		});

		assertTrue(exception.getMessage().contains("cannot be null"));
	}

	@Test
	void testUpdateTvPlan() throws SubscriptionException {
		TvPlan savedPlan = tvService.addOrUpdateTvPlan(validPlan);

		savedPlan.setPlanName("Updated TV Plan");
		TvPlan updatedPlan = tvService.addOrUpdateTvPlan(savedPlan);

		assertEquals("Updated TV Plan", updatedPlan.getPlanName());
	}

	@Test
	void testDeleteTvPlan_ValidId() throws SubscriptionException {
		TvPlan savedPlan = tvService.addOrUpdateTvPlan(validPlan);

		assertTrue(tvService.deleteTvPlan(savedPlan.getTvId()));
	}

	@Test
	void testDeleteTvPlan_InvalidId() {
		Exception exception = assertThrows(SubscriptionException.class, () -> {
			tvService.deleteTvPlan(9999L);
		});

		assertTrue(exception.getMessage().contains("not found"));
	}

	@Test
	void testDeleteTvPlan_NullId() {
		Exception exception = assertThrows(SubscriptionException.class, () -> {
			tvService.deleteTvPlan(null);
		});

		assertTrue(exception.getMessage().contains("cannot be null"));
	}

	@Test
	void testGetAllPlans_WithData() throws SubscriptionException {
		TvPlan savedPlan1 = tvService.addOrUpdateTvPlan(validPlan);

		TvPlan secondPlan = new TvPlan();
		secondPlan.setPlanName("Premium TV Plan");
		secondPlan.setPremiumChannels(50);
		secondPlan.setMonthlyCost(50.0);
		secondPlan.setDiscount(20.0);
		TvPlan savedPlan2 = tvService.addOrUpdateTvPlan(secondPlan);

		var plans = tvService.getAllPlans(0, 10, "tvId", "ASC");

		assertEquals(2, plans.getContent().size());
		assertTrue(plans.getContent().get(0).getTvId() < plans.getContent().get(1).getTvId());
		assertEquals(savedPlan1, plans.getContent().get(0));
		assertEquals(savedPlan2, plans.getContent().get(1));
	}

	@Test
	void testGetAllPlans_InvalidPageParameters() {
		Exception exception = assertThrows(Exception.class, () -> {
			tvService.getAllPlans(-1, -10, "tvId", "ASC");
		});

		assertTrue(exception.getMessage().contains("Page index must not be less than zero"));
	}
}