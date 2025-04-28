package com.ramyakata.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramyakata.microservice.entity.WifiPlan;

/**
 * Description: Repository interface for managing WifiPlan entities. Provides
 * CRUD operations and query capabilities for the WifiPlan table.
 */
public interface WifiPlanRepo extends JpaRepository<WifiPlan, Long> {

}
