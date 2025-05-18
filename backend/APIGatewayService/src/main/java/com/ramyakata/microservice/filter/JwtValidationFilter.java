package com.ramyakata.microservice.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.ramyakata.microservice.aop.LoggingAspect;
import com.ramyakata.microservice.service.JWTService;

import reactor.core.publisher.Mono;

/**
 * Custom Gateway filter for validating JWT tokens and enforcing role-based
 * access.
 * <p>
 * This filter is applied to all incoming requests via Spring Cloud Gateway. It
 * performs the following:
 * <ul>
 * <li>Skips validation for public endpoints (login/register)</li>
 * <li>Validates the Authorization header and JWT structure</li>
 * <li>Extracts the user's role from the token</li>
 * <li>Compares it with allowed route roles defined in route metadata</li>
 * <li>Adds a custom header (X-Authenticated-Role) for downstream
 * microservices</li>
 * </ul>
 * <p>
 * If validation fails, an appropriate HTTP error response is returned.
 * <p>
 * This ensures centralized security handling at the API Gateway level.
 * 
 * @see com.ramyakata.microservice.service.JWTService
 * @see org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 * 
 * @author Ramya Kata
 */
@Component
public class JwtValidationFilter extends AbstractGatewayFilterFactory<JwtValidationFilter.Config> {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Autowired
	private JWTService jwtService;

	/**
	 * Constructor for the JWT validation filter.
	 */
	public JwtValidationFilter() {
		super(Config.class);
	}

	/**
	 * Applies the JWT validation logic to each request that passes through the API
	 * Gateway.
	 * 
	 * @param config filter configuration (unused here but required by factory
	 *               signature)
	 * @return GatewayFilter functional implementation
	 */
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String path = exchange.getRequest().getURI().getPath();

			// Skip validation for login and register routes
			if (path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
				return chain.filter(exchange);
			}

			String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				return onError(exchange, "Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
			}

			String token = authHeader.substring(7);
			try {
				// Validate token
				if (!jwtService.validateToken(token)) {
					return onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
				}

				// Extract role from token
				String role = jwtService.extractRoleFromToken(token).toUpperCase();
				logger.info("Extracted Role: {}", role);

				// Retrieve allowed roles from route metadata
				Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
				if (route == null) {
					return onError(exchange, "Route not found", HttpStatus.NOT_FOUND);
				}

				Object rolesObject = route.getMetadata().getOrDefault("roles", Collections.emptyList());
				List<String> allowedRoles = (rolesObject instanceof List) ? (List<String>) rolesObject
						: Arrays.asList(rolesObject.toString().split(","));

				logger.info("Allowed Roles for Route: {}", allowedRoles);

				if (!allowedRoles.stream().map(String::toUpperCase).collect(Collectors.toList())
						.contains(role.toUpperCase())) {
					return onError(exchange, "Forbidden: Role not allowed", HttpStatus.FORBIDDEN);
				}

				// Add user details to headers for downstream services
				exchange.getRequest().mutate().header("X-Authenticated-Role", role).build();

			} catch (Exception e) {
				logger.error("JWT validation error: {}", e.getMessage(), e);
				return onError(exchange, "JWT validation error: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
			}

			return chain.filter(exchange);
		};
	}
	/**
     * Utility method to return an error response with a specific status code.
     *
     * @param exchange the current server exchange
     * @param error the error message to include in the log
     * @param status the HTTP status to return
     * @return Mono<Void> reactive response
     */

	private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus status) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}
	
	/**
     * Configuration class placeholder for the filter.
     * Can be extended to pass properties in the future.
     */

	public static class Config {
		// Add custom configurations if needed
	}
}