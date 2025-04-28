package com.ramyakata.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ramyakata.microservice.entity.Address;

/**
 * Repository interface for performing CRUD operations on the Address entity.
 * 
 */
@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

}
