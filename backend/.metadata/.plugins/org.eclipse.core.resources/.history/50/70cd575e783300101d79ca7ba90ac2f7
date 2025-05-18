package com.ramyakata.microservice.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


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
