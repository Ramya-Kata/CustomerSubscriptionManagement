package com.ramyakata.microservice.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ramyakata.microservice.dao.AuthService;
import com.ramyakata.microservice.dto.UserDto;
import com.ramyakata.microservice.entity.Status;
import com.ramyakata.microservice.entity.UserPrinciple;
import com.ramyakata.microservice.entity.Users;
import com.ramyakata.microservice.exception.AuthException;
import com.ramyakata.microservice.repo.UserRepo;

/**
 * Service implementation for user authentication, registration, and retrieval
 * logic.
 * <p>
 * This class handles:
 * <ul>
 * <li>User registration with validation and password encryption</li>
 * <li>Login verification and JWT token generation</li>
 * <li>User data retrieval by username</li>
 * </ul>
 * 
 * It is the concrete implementation of the {@link AuthService} interface, and
 * is used by {@link com.ramyakata.microservice.controller.AuthController}.
 * 
 * @see com.ramyakata.microservice.entity.Users
 * @see com.ramyakata.microservice.dto.UserDto
 * @see com.ramyakata.microservice.impl.JWTService
 * @see com.ramyakata.microservice.repo.UserRepo
 * 
 *      Author: Ramya Kata
 */
@Service
public class UserService implements AuthService {

	@Autowired
	public UserRepo userRepo;

	@Autowired
	AuthenticationManager manager;

	@Autowired
	private JWTService jwtService;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	/**
	 * Registers a new user after validation.
	 *
	 * @param user the user details to register
	 * @return true if successful
	 * @throws AuthException if user already exists or fields are invalid
	 */
	@Override
	public Boolean insert(Users user) throws AuthException {

		if (user == null) {
			throw new AuthException("User object cannot be null.");
		}

		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new AuthException("Email cannot be null or empty.");
		}

		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			throw new AuthException("Username cannot be null or empty.");
		}

		// Existing user validation
		Users existingUser = userRepo.findByEmail(user.getEmail());
		if (existingUser != null) {
			throw new AuthException("Email " + user.getEmail() + " already exists.");
		}

		// Unique username validation
		Users existingUserByUsername = userRepo.findByUsername(user.getUsername());
		if (existingUserByUsername != null) {
			throw new AuthException("User with Username " + user.getUsername() + " already exists.");
		}

		// Assign defaults and encode password
		if (user.getRole() == null || user.getRole().isEmpty()) {
			user.setRole("user");
		}
		user.setPassword(encoder.encode(user.getPassword()));

		user.setCreatedAt(new Date());
		user.setLastLogin(new Date());
		user.setStatus(Status.active);
		userRepo.insert(user);

		return true;

	}

	/**
	 * Retrieves a user by username and returns a UserDto (without password).
	 *
	 * @param username the username/email of the user
	 * @return sanitized {@link UserDto} with user details
	 * @throws AuthException if user is not found or input is invalid
	 */
	@Override
	public UserDto getByUserName(String username) throws AuthException {
		if (username == null || username.isEmpty()) {
			throw new AuthException("Email cannot be null or empty.");
		}

		Users user = userRepo.findByUsername(username);
		if (user == null) {
			throw new AuthException("No user found with email: " + username);
		}

		return new UserDto(user.getUsername(), user.getEmail(), user.getLastLogin(), user.getRole());
	}

//	@Override
//	public String verify(Users user) throws AuthException {
//		// TODO Auto-generated method stub
//		if (user == null) {
//			throw new AuthException("User object cannot be null.");
//		}
//
//		if (user.getUsername() == null || user.getUsername().isEmpty()) {
//			throw new AuthException("Username cannot be null or empty.");
//		}
//
//		if (user.getPassword() == null || user.getPassword().isEmpty()) {
//			throw new AuthException("Password cannot be null or empty.");
//		}
//
//		try {
//			Authentication authentication = manager
//					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//			if (authentication.isAuthenticated()) {
//				if (user.getLastLogin() == null) {
//					user.setLastLogin(new Date());
//				}
//				return jwtService.generateToken(user.getUsername(), user.getRole());
//			}
//		} catch (Exception ex) {
//			throw new AuthException("Invalid username or password.");
//		}
//		return "fail";
//	}

	/**
	 * Verifies the user's credentials and returns a signed JWT token.
	 *
	 * @param user the login credentials (username and password)
	 * @return JWT token on successful authentication
	 * @throws AuthException if credentials are invalid or missing
	 */
	@Override
	public String verify(Users user) throws AuthException {
		if (user == null) {
			throw new AuthException("User object cannot be null.");
		}

		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			throw new AuthException("Username cannot be null or empty.");
		}

		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new AuthException("Password cannot be null or empty.");
		}

		try {
			Authentication authentication = manager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

			if (authentication.isAuthenticated()) {
				// Extract UserPrinciple from Authentication
				UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
				String role = userPrinciple.getAuthorities().iterator().next().getAuthority();

				user.setLastLogin(new Date());

				// Generate token with username and role
				return jwtService.generateToken(userPrinciple.getUsername(), role);
			}
		} catch (Exception ex) {
			throw new AuthException("Invalid username or password.");
		}
		return "fail";
	}

}
