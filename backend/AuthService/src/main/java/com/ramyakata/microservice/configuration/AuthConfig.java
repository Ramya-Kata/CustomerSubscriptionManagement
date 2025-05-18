package com.ramyakata.microservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ramyakata.microservice.filter.JwtFilter;

/**
 * Configuration class for Spring Security in the Authentication Service.
 * <p>
 * This class sets up:
 * <ul>
 *   <li>JWT-based authentication and stateless session management</li>
 *   <li>Custom {@link AuthenticationProvider} using a {@link UserDetailsService}</li>
 *   <li>Public access to <code>/auth/register</code> and <code>/auth/login</code></li>
 *   <li>A custom {@link JwtFilter} for request validation</li>
 * </ul>
 * 
 * It uses Spring Security's filter chain and disables default form login
 * in favor of token-based authentication.
 * 
 * ⚠️ Note: For production, password encoding should be enforced with stronger encoders like BCrypt.
 * 
 * @see com.ramyakata.microservice.filter.JwtFilter
 * @see org.springframework.security.authentication.AuthenticationProvider
 * @see org.springframework.security.crypto.password.PasswordEncoder
 * @see org.springframework.security.web.SecurityFilterChain
 * 
 * @author Ramya Kata
 */
@Configuration
@EnableWebSecurity
public class AuthConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(customizer -> customizer.disable()).authorizeHttpRequests(request -> request
				.requestMatchers("/auth/register", "/auth/login").permitAll().anyRequest().authenticated())
//		    .formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());//no password encoder for now  
		provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
//		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString(); // No encoding
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.toString().equals(encodedPassword);
			}
		};
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();

	}
}
