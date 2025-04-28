//package com.indus.training.microservice;
//
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.TestPropertySource;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
//		"eureka.client.enabled=false" })
//@TestPropertySource(properties = { "plan.service.url=http://localhost:8082" })
//public class CalculationServiceIntegrationTest {
//
//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	private MockWebServer mockWebServer;
//
//	@BeforeEach
//	void setUp() throws Exception {
//		// Start the mock server
//		mockWebServer = new MockWebServer();
//		mockWebServer.start();
//
//		// Set the mock server's URL to Feign client or RestTemplate
//		String mockUrl = mockWebServer.url("/").toString();
//		System.setProperty("plan.service.url", mockUrl);
//	}
//
//	@AfterEach
//	void tearDown() throws Exception {
//		// Shut down the mock server
//		mockWebServer.shutdown();
//	}
//
//	@Test
//	void testCalculatePayment() {
//		// Mock a successful response from Plan Service
//		mockWebServer.enqueue(new MockResponse()
//				.setBody("{\"id\":4440059,\"planName\":\"Basic Wifi Plan\",\"monthlyCost\":15.0,\"discount\":10.0}")
//				.addHeader("Content-Type", "application/json"));
//
//		// Prepare the test endpoint URL
//		String url = "http://localhost:" + port
//				+ "/calculate/proxy/amount?planType=wifi&planId=4440059&numberOfMonths=quarterly";
//
//		// Call the endpoint
//		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//		// Assert the response
//		assertEquals(200, response.getStatusCode());
//		assertNotNull(response.getBody());
//
//		// You can also parse the response and validate its content
//		String responseBody = response.getBody();
//		System.out.println("Response: " + responseBody);
//	}
//}
package com;


