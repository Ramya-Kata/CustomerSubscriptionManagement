package com.ramyakata.microservice.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ramyakata.microservice.entity.Users;

/**
 * Repository interface for accessing and managing {@link Users} entities in
 * MongoDB.
 * <p>
 * Extends Spring Data's {@link MongoRepository} to provide built-in CRUD
 * operations.
 * <p>
 * Custom query methods:
 * <ul>
 * <li>{@code findByUsername(String username)} – retrieves a user by their
 * username</li>
 * <li>{@code findByEmail(String email)} – retrieves a user by their email
 * address</li>
 * </ul>
 * 
 * This interface allows for seamless integration with the MongoDB data layer
 * without writing any boilerplate queries.
 * 
 * @see com.ramyakata.microservice.entity.Users
 * @see org.springframework.data.mongodb.repository.MongoRepository
 * 
 *      Author: Ramya Kata
 */
@Repository
public interface UserRepo extends MongoRepository<Users, ObjectId> {

	/**
	 * Finds a user by username.
	 *
	 * @param username the username to search for
	 * @return the matching {@link Users} object, or null if not found
	 */
	Users findByUsername(String username);

	/**
	 * Finds a user by email address.
	 *
	 * @param email the email to search for
	 * @return the matching {@link Users} object, or null if not found
	 */
	Users findByEmail(String email);
}
