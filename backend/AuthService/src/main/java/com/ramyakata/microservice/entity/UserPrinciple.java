package com.ramyakata.microservice.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom implementation of Spring Security's {@link UserDetails} interface.
 * <p>
 * This class wraps the application's {@link Users} entity to integrate it with
 * Spring Security's authentication and authorization framework.
 * <p>
 * It provides user credentials and roles to the security context during login.
 * 
 * @see com.ramyakata.microservice.entity.Users
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see org.springframework.security.authentication.AuthenticationProvider
 * 
 *      Example usage: - Returned by
 *      {@code UserDetailsServiceImpl.loadUserByUsername()} - Used by Spring
 *      Security during authentication flow
 * 
 * @author Ramya Kata
 */
public class UserPrinciple implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Users user;

	/**
	 * Constructor that wraps a {@link Users} entity.
	 *
	 * @param user the application user entity
	 */
	public UserPrinciple(Users user) {
		this.user = user;
	}

	/**
	 * Returns the authorities granted to the user.
	 * <p>
	 * This includes the role from the user entity, converted into a
	 * {@link SimpleGrantedAuthority} object.
	 *
	 * @return collection of granted authorities (roles)
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		// return Collections.singleton(new SimpleGrantedAuthority("USER"));
		return List.of(new SimpleGrantedAuthority(user.getRole()));
	}

	/**
	 * Returns the user's password.
	 *
	 * @return the encoded password
	 */
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	/**
	 * Returns the username used to authenticate the user.
	 *
	 * @return the username
	 */
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

}
