package com.ramyakata.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * * Entry point for the API Gateway microservice.
 * <p>
 * This service acts as the centralized entry point to route client requests to
 * the appropriate backend microservices using Spring Cloud Gateway.
 * <p>
 * It also enables service discovery through Eureka, allowing dynamic resolution
 * of service instances registered in the service registry.
 * 
 * Annotations: - {@link SpringBootApplication} marks this as a Spring Boot app.
 * - {@link ComponentScan} scans all components under the specified base
 * package. - {@link EnableDiscoveryClient} allows this service to register with
 * Eureka. - {@link EnableAspectJAutoProxy} enables support for aspect-oriented
 * programming.
 * 
 * @author Ramya Kata
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.ramyakata.microservice" })
@EnableDiscoveryClient
@EnableAspectJAutoProxy
public class ApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServiceApplication.class, args);
	}

}
