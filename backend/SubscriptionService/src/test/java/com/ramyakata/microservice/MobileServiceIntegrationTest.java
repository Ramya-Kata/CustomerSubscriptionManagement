package com.ramyakata.microservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ramyakata.microservice.dto.PaginatedResponse;
import com.ramyakata.microservice.entity.MobilePlan;
import com.ramyakata.microservice.repo.MobilePlanRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MobileServiceIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private MobilePlan validPlan;
	private MobilePlan invalidPlan;

	@Autowired
	private MobilePlanRepo mobilePlanRepo;

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
	void testAddOrUpdateMobilePlan_ValidPlan() {
		ResponseEntity<MobilePlan> response = restTemplate.postForEntity("/plan/mobile/update", validPlan,
				MobilePlan.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Basic Plan", response.getBody().getPlanName());
	}

	@Test
	void testAddOrUpdateMobilePlan_InvalidPlan() {
		// Send a request with an invalid plan
		ResponseEntity<String> response = restTemplate.postForEntity("/plan/mobile/update", invalidPlan, String.class);

		// Validate HTTP status
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Expected HTTP status BAD_REQUEST");

		// Validate error message

		assertTrue(response.getBody().contains("Invalid"));
	}

	@Test
	void testGetAllMobilePlans() {
		// Add two plans
		restTemplate.postForEntity("/plan/mobile/update", validPlan, MobilePlan.class);

		MobilePlan secondPlan = new MobilePlan();
		secondPlan.setPlanName("Second Plan");
		secondPlan.setDataLimit("10GB");
		secondPlan.setCallMinutes(200);
		secondPlan.setTextLimit(100);
		secondPlan.setMonthlyCost(20.0);
		secondPlan.setDiscount(10.0);
		restTemplate.postForEntity("/plan/mobile/update", secondPlan, MobilePlan.class);

		// Fetch all plans with pagination
		ResponseEntity<PaginatedResponse<MobilePlan>> response = restTemplate.exchange(
				"/plan/mobile/get/all?pageNumber=0&pageSize=10", HttpMethod.GET, null,
				new ParameterizedTypeReference<PaginatedResponse<MobilePlan>>() {
				});

		// Validate response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().getContent().size(), "The number of mobile plans should be 2");
	}

	@Test
	void testDeleteMobilePlan() {
		// Step 1: Add a plan
		MobilePlan savedPlan = restTemplate.postForEntity("/plan/mobile/update", validPlan, MobilePlan.class).getBody();
		assertNotNull(savedPlan);

		// Step 2: Verify plan exists before deletion
		String getUrl = "/plan/mobile/get/all?pageNumber=0&pageSize=10";
		ResponseEntity<PaginatedResponse<MobilePlan>> responseBeforeDelete = restTemplate.exchange(getUrl,
				HttpMethod.GET, null, new ParameterizedTypeReference<PaginatedResponse<MobilePlan>>() {
				});
		assertEquals(1, responseBeforeDelete.getBody().getContent().size(),
				"There should be 1 mobile plan before deletion.");

		// Step 3: Delete the plan
		restTemplate.delete("/plan/mobile/delete/" + savedPlan.getmobileId());

		// Step 4: Verify plan does not exist after deletion
		ResponseEntity<PaginatedResponse<MobilePlan>> responseAfterDelete = restTemplate.exchange(getUrl,
				HttpMethod.GET, null, new ParameterizedTypeReference<PaginatedResponse<MobilePlan>>() {
				});
		assertTrue(responseAfterDelete.getBody().getContent().isEmpty(),
				"The mobile plans should be empty after deletion.");
	}
}
