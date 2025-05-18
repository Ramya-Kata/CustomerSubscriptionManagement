package com.ramyakata.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Entry point for the User Service application.
 * <p>
 * This service handles customer-related operations such as registration,
 * profile updates, and user data management.
 * <p>
 * Key annotations:
 * <ul>
 * <li>{@link SpringBootApplication} - Enables Spring Boot
 * auto-configuration</li>
 * <li>{@link ComponentScan} - Scans components under
 * <code>com.ramyakata.microservice</code></li>
 * <li>{@link EnableAspectJAutoProxy} - Enables AOP-based cross-cutting concerns
 * (e.g., logging)</li>
 * <li>{@link EnableDiscoveryClient} - Registers the service with Eureka</li>
 * </ul>
 * 
 * Author: Ramya Kata
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.ramyakata.microservice" })
@EnableAspectJAutoProxy
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
