package com.ramyakata.microservice.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * Global CORS configuration for the API Gateway.
 * <p>
 * This configuration allows cross-origin requests from the frontend to access 
 * backend microservices through the gateway.
 * <p>
 * CORS settings include:
 * <ul>
 *     <li>Allowed origins</li>
 *     <li>HTTP methods (GET, POST, PUT, DELETE)</li>
 *     <li>Custom headers such as Authorization</li>
 *     <li>Credential sharing</li>
 * </ul>
 * <p>
 * By registering this filter at the gateway level, all incoming 
 * requests from the frontend must go through this configuration 
 * before reaching downstream services.
 * 
 * @author Ramya Kata
 */

@Configuration
public class GlobalCorsConfig {

	 @Bean
	    public CorsWebFilter corsWebFilter() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOrigins(List.of("http://localhost:3000")); // Frontend URL
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // HTTP methods
	        config.setAllowedHeaders(List.of("*")); // Allow all headers
	        config.setExposedHeaders(List.of("Authorization", "X-Authenticated-Role")); // Expose custom headers
	        config.setAllowCredentials(true); // Allow credentials (e.g., cookies, Authorization headers)

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config); // Apply to all routes

	        return new CorsWebFilter(source);
	    }
}
