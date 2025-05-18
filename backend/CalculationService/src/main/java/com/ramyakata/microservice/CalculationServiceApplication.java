package com.ramyakata.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Entry point for the Calculation Service application.
 * <p>
 * This service performs subscription cost calculations based on user input or
 * external plan data retrieved via Feign clients.
 * <p>
 * Key annotations:
 * <ul>
 * <li>{@link SpringBootApplication} - Enables Spring Boot
 * auto-configuration</li>
 * <li>{@link ComponentScan} - Scans components under
 * <code>com.ramyakata.microservice</code></li>
 * <li>{@link EnableFeignClients} - Enables Feign clients for inter-service
 * communication</li>
 * <li>{@link EnableDiscoveryClient} - Registers this service with Eureka</li>
 * </ul>
 * 
 * Author: Ramya Kata
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.ramyakata.microservice" })
@EnableFeignClients(basePackages = "com.ramyakata.microservice.repo")
@EnableDiscoveryClient
public class CalculationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculationServiceApplication.class, args);
	}

}
