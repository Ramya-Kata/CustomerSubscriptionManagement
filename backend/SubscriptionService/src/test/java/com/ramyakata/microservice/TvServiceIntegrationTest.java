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
import com.ramyakata.microservice.entity.TvPlan;
import com.ramyakata.microservice.repo.TvPlanRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TvServiceIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private TvPlan validPlan;
	private TvPlan invalidPlan;

	@Autowired
	private TvPlanRepo tvPlanRepo;

	@BeforeEach
	void setUp() {
		tvPlanRepo.deleteAll();

		validPlan = new TvPlan();
		validPlan.setPlanName("Basic TV Plan");
		validPlan.setPremiumChannels(10);
		validPlan.setMonthlyCost(20.0);
		validPlan.setDiscount(5.0);

		invalidPlan = new TvPlan();
		invalidPlan.setPlanName(null);
	}

	@Test
	void testAddOrUpdateTvPlan_ValidPlan() {
		ResponseEntity<TvPlan> response = restTemplate.postForEntity("/plan/tv/update", validPlan, TvPlan.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Basic TV Plan", response.getBody().getPlanName());
	}

	@Test
	void testAddOrUpdateTvPlan_InvalidPlan() {
		ResponseEntity<String> response = restTemplate.postForEntity("/plan/tv/update", invalidPlan, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Tv plan cannot be null"));

	}

	@Test
	void testGetAllTvPlans() {
		restTemplate.postForEntity("/plan/tv/update", validPlan, TvPlan.class);

		TvPlan secondPlan = new TvPlan();
		secondPlan.setPlanName("Premium TV Plan");
		secondPlan.setPremiumChannels(50);
		secondPlan.setMonthlyCost(50.0);
		secondPlan.setDiscount(20.0);
		restTemplate.postForEntity("/plan/tv/update", secondPlan, TvPlan.class);

		ResponseEntity<PaginatedResponse<TvPlan>> response = restTemplate.exchange(
				"/plan/tv/get/all?pageNumber=0&pageSize=10", HttpMethod.GET, null,
				new ParameterizedTypeReference<PaginatedResponse<TvPlan>>() {
				});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().getContent().size(), "The number of TV plans should be 2");
	}

	@Test
	void testDeleteTvPlan() {
		TvPlan savedPlan = restTemplate.postForEntity("/plan/tv/update", validPlan, TvPlan.class).getBody();
		assertNotNull(savedPlan);

		String getUrl = "/plan/tv/get/all?pageNumber=0&pageSize=10";
		ResponseEntity<PaginatedResponse<TvPlan>> responseBeforeDelete = restTemplate.exchange(getUrl, HttpMethod.GET,
				null, new ParameterizedTypeReference<PaginatedResponse<TvPlan>>() {
				});
		assertEquals(1, responseBeforeDelete.getBody().getContent().size(),
				"There should be 1 TV plan before deletion.");

		restTemplate.delete("/plan/tv/delete/" + savedPlan.getTvId());

		ResponseEntity<PaginatedResponse<TvPlan>> responseAfterDelete = restTemplate.exchange(getUrl, HttpMethod.GET,
				null, new ParameterizedTypeReference<PaginatedResponse<TvPlan>>() {
				});
		assertTrue(responseAfterDelete.getBody().getContent().isEmpty(),
				"The TV plans should be empty after deletion.");
	}
}
